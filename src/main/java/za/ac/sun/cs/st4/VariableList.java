package za.ac.sun.cs.st4;

import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

public class VariableList {
    @Parameter
    private List<String> variables;

    public List<String> getVariables() {
        return variables;
    }

    @Override
    public String toString() { return variables.toString(); }
}
