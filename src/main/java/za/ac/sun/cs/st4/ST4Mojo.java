package za.ac.sun.cs.st4;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.stringtemplate.v4.*;

import java.io.File;

@Mojo(name = "render", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ST4Mojo extends AbstractMojo {
    /*
     * where to look for templates */
    @Parameter(property = "sourceDirectory", defaultValue = "${basedir}/src/main/resources/templates")
    private File sourceDirectory;
    /*
     * where to keep generated files
     */
    @Parameter(property = "outputDirectory",defaultValue = "${project.build.directory}/generated-sources/templates")
    private File outputDirectory;

    @Parameter(defaultValue = "${project}")
    MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("***  st4-maven-plugin (su edition) ***");
        getLog().info("This is a minimal plugin for stringtemplate");

    }
}
