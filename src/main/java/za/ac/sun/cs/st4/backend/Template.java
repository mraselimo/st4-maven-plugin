package za.ac.sun.cs.st4.backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.ErrorBuffer;

import za.ac.sun.cs.st4.ST4Mojo;

public class Template {
    static Map<String, Object> properties;
    public static void setProperties(Map<String, Object> props) {
        properties = props;
    }
    private static Map<String, Object> getProperties() {
        return properties;
    }
    public static void generateSource(String fileName) throws IOException {
        String tFile = "templates/metatemplate.stg";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(tFile).getFile());

        STGroup group  = new STGroupFile(file.getPath()); //TODO
        ST st = group.getInstanceOf("meta");

        FileWriter fileWriter = new FileWriter(fileName);
        ErrorBuffer listener = new ErrorBuffer();

        Map<String, Object> members = getProperties();
        st.add("members", members);

        st.write(new AutoIndentWriter(fileWriter), listener);
        fileWriter.flush();
        fileWriter.close();
    }
}
