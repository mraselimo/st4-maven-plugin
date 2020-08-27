package za.ac.sun.cs.st4;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.ErrorBuffer;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Mojo(name = "render", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ST4Mojo extends AbstractMojo {

    @Parameter(property = "outputDirectory",defaultValue = "${project.build.directory}/generated-sources/templates")
    private File outputDirectory;

    @Parameter
    List<Template> templates;

    @Parameter(defaultValue = "${project}")
    MavenProject project;
    
    @Parameter(property = "props", defaultValue="")
    private List<String> props;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("*******************************************");
        getLog().info("       st4-maven-plugin (su edition)       ");
        getLog().info("This is a minimal plugin for stringtemplate");
        getLog().info("*******************************************");

        if(!outputDirectory.exists())
            outputDirectory.mkdirs();

        for (Template template : templates) {
            File templateSource = template.getSourceDirectory();
            String templateFile = template.getTemplateFile();
            String target = template.getTarget();
            String generatedFile = template.getGeneratedFileName();
            String tempFile = templateSource.getAbsolutePath()+"/"+templateFile;
            String outputFile = outputDirectory.getAbsolutePath()+"/"+generatedFile;
            try {
                template.render(tempFile, target, outputFile, props);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /* tell maven where to look for generated sources */
        if(project != null) {
            project.addCompileSourceRoot(outputDirectory.getAbsolutePath());
        }
    }
}
