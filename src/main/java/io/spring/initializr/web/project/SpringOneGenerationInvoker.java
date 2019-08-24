package io.spring.initializr.web.project;

import com.springone.initializr.springoneinitializr.config.SpringOneWebProjectRequest;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.buildsystem.BuildWriter;
import io.spring.initializr.generator.project.DefaultProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import io.spring.initializr.generator.project.ProjectGenerationException;
import io.spring.initializr.generator.project.ProjectGenerator;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.FileSystemUtils;

public class SpringOneGenerationInvoker extends ProjectGenerationInvoker {

	private final ApplicationContext parentApplicationContext;

	private final ApplicationEventPublisher eventPublisher;

	private final ProjectRequestToDescriptionConverter converter;

	private transient Map<Path, List<Path>> temporaryFiles = new LinkedHashMap<>();

	public SpringOneGenerationInvoker(GenericApplicationContext parentApplicationContext,
			ApplicationEventPublisher eventPublisher, ProjectRequestToDescriptionConverter converter) {
		super(parentApplicationContext, eventPublisher, converter);
		this.parentApplicationContext = parentApplicationContext;
		this.eventPublisher = eventPublisher;
		this.converter = converter;

	}

	/**
	 * Invokes the project generation API that generates the entire project
	 * structure for the specified {@link WebProjectRequest}.
	 * 
	 * @param request the project request
	 * @return the {@link ProjectGenerationResult}
	 */
	@Override
	public ProjectGenerationResult invokeProjectStructureGeneration(ProjectRequest request) {
		InitializrMetadata metadata = this.parentApplicationContext.getBean(InitializrMetadataProvider.class).get();
		try {
			ProjectDescription projectDescription = this.converter.convert(request, metadata);
			ProjectGenerator projectGenerator = new ProjectGenerator(
					(projectGenerationContext) -> customizeProjectGenerationContext(projectGenerationContext, metadata,
							(SpringOneWebProjectRequest) request));
			ProjectGenerationResult result = projectGenerator.generate(projectDescription, generateProject(request));
			addTempFile(result.getRootDirectory(), result.getRootDirectory());
			return result;
		} catch (ProjectGenerationException ex) {
			publishProjectFailedEvent(request, metadata, ex);
			throw ex;
		}
	}

	private ProjectAssetGenerator<ProjectGenerationResult> generateProject(ProjectRequest request) {
		return (context) -> {
			Path projectDir = new DefaultProjectAssetGenerator().generate(context);
			publishProjectGeneratedEvent(request, context);
			return new ProjectGenerationResult(context.getBean(ResolvedProjectDescription.class), projectDir);
		};
	}

	/**
	 * Invokes the project generation API that knows how to just write the build
	 * file. Returns a directory containing the project for the specified
	 * {@link WebProjectRequest}.
	 * 
	 * @param request the project request
	 * @return the generated build content
	 */
	@Override
	public byte[] invokeBuildGeneration(ProjectRequest request) {
		InitializrMetadata metadata = this.parentApplicationContext.getBean(InitializrMetadataProvider.class).get();
		try {
			ProjectDescription projectDescription = this.converter.convert(request, metadata);
			ProjectGenerator projectGenerator = new ProjectGenerator(
					(projectGenerationContext) -> customizeProjectGenerationContext(projectGenerationContext, metadata,
							(SpringOneWebProjectRequest) request));
			return projectGenerator.generate(projectDescription, generateBuild(request));
		} catch (ProjectGenerationException ex) {
			publishProjectFailedEvent(request, metadata, ex);
			throw ex;
		}
	}

	private ProjectAssetGenerator<byte[]> generateBuild(ProjectRequest request) {
		return (context) -> {
			byte[] content = generateBuild(context);
			publishProjectGeneratedEvent(request, context);
			return content;
		};
	}

	/**
	 * Create a file in the same directory as the given directory using the
	 * directory name and extension.
	 * 
	 * @param dir       the directory used to determine the path and name of the new
	 *                  file
	 * @param extension the extension to use for the new file
	 * @return the newly created file
	 */
	@Override
	public Path createDistributionFile(Path dir, String extension) {
		Path download = dir.resolveSibling(dir.getFileName() + extension);
		addTempFile(dir, download);
		return download;
	}

	private void addTempFile(Path group, Path file) {
		this.temporaryFiles.computeIfAbsent(group, (key) -> new ArrayList<>()).add(file);
	}

	/**
	 * Clean all the temporary files that are related to this root directory.
	 * 
	 * @param dir the directory to clean
	 * @see #createDistributionFile
	 */
	@Override
	public void cleanTempFiles(Path dir) {
		List<Path> tempFiles = this.temporaryFiles.remove(dir);
		if (!tempFiles.isEmpty()) {
			tempFiles.forEach((path) -> {
				try {
					FileSystemUtils.deleteRecursively(path);
				} catch (IOException ex) {
					// Continue
				}
			});
		}
	}

	private byte[] generateBuild(ProjectGenerationContext context) throws IOException {
		ResolvedProjectDescription projectDescription = context.getBean(ResolvedProjectDescription.class);
		StringWriter out = new StringWriter();
		BuildWriter buildWriter = context.getBeanProvider(BuildWriter.class).getIfAvailable();
		if (buildWriter != null) {
			buildWriter.writeBuild(out);
			return out.toString().getBytes();
		} else {
			throw new IllegalStateException(
					"No BuildWriter implementation found for " + projectDescription.getLanguage());
		}
	}

	private void customizeProjectGenerationContext(AnnotationConfigApplicationContext context,
			InitializrMetadata metadata, SpringOneWebProjectRequest request) {
		context.setParent(this.parentApplicationContext);
		context.registerBean(SpringOneWebProjectRequest.class, () -> request);
		context.registerBean(InitializrMetadata.class, () -> metadata);
		context.registerBean(BuildItemResolver.class, () -> new MetadataBuildItemResolver(metadata,
				context.getBean(ResolvedProjectDescription.class).getPlatformVersion()));
	}

	private void publishProjectGeneratedEvent(ProjectRequest request, ProjectGenerationContext context) {
		InitializrMetadata metadata = context.getBean(InitializrMetadata.class);
		ProjectGeneratedEvent event = new ProjectGeneratedEvent(request, metadata);
		this.eventPublisher.publishEvent(event);
	}

	private void publishProjectFailedEvent(ProjectRequest request, InitializrMetadata metadata, Exception cause) {
		ProjectFailedEvent event = new ProjectFailedEvent(request, metadata, cause);
		this.eventPublisher.publishEvent(event);
	}

}
