package za.ac.sun.cs.st4.backend;

import java.io.FileWriter;
import java.io.IOException;

import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.ErrorBuffer;

public class Template {
    public static void generateSource(String fileName) throws IOException {
        STGroup group  = new STGroupFile(""); //TODO
        ST st = group.getInstanceOf("meta");

        FileWriter fileWriter = new FileWriter(fileName);
        ErrorBuffer listener = new ErrorBuffer();
        // aa big TODO here
        st.write(new AutoIndentWriter(fileWriter), listener);
        fileWriter.flush();
        fileWriter.close();
    }
}
