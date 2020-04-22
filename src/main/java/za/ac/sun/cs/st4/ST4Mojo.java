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
import java.util.Map;

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

    /*
    * template file
    **/
    @Parameter(property = "template")
    private String template;
    /*
    * name of the template to process
    **/
    @Parameter(property = "target", defaultValue = "")
    private String target;

    /*
    *  name of generated source file
    * */
    @Parameter(property = "generatedClassName", defaultValue = "")
    private String generatedClassName;

    @Parameter(property = "properties")
    private Map<String, String> properties;

    @Parameter(defaultValue = "${project}")
    MavenProject project;

    private void generateSources(String templateFile, String templateName, String generatedFile) throws IOException {
        STGroup group = new STGroupFile(templateFile);
        ST st = group.getInstanceOf(templateName);

        FileWriter writer = new FileWriter(generatedFile);
        ErrorBuffer listener = new ErrorBuffer();

        for(String key : this.getProperties().keySet()) {
            st.add(key, this.getProperties().get(key));
        }
        st.write(new AutoIndentWriter(writer), listener);
        writer.flush();
        writer.close();
    }

    private Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("*******************************************");
        getLog().info("       st4-maven-plugin (su edition)       ");
        getLog().info("This is a minimal plugin for stringtemplate");
        getLog().info("*******************************************");

        if(!outputDirectory.exists())
            outputDirectory.mkdirs();

        String templateFile = sourceDirectory.getAbsolutePath() + template;
        String outputFile = outputDirectory.getAbsolutePath() + generatedClassName;
        try {
            this.generateSources(templateFile, target, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* tell maven where to look for generated sources */
        if(project != null) {
            project.addCompileSourceRoot(outputDirectory.getAbsolutePath());
        }
    }
}
