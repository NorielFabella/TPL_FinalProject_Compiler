package TPLProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
public class SemanticAnalysisAction implements ActionListener {
    private Compiler compiler;
    private Set<String> declaredVariables;
    private Map<String, String> variableTypes;

    public SemanticAnalysisAction(Compiler compiler) {
        this.compiler = compiler;
        this.declaredVariables = new HashSet<>();
        this.variableTypes = new HashMap<>();

    }

    public void actionPerformed(ActionEvent e) {
        AnalyzeSemantic();
    }

    private void AnalyzeSemantic() {

        declaredVariables.clear();
        variableTypes.clear();

        String code = compiler.getCodeTextArea().getText();
        String[] lines = code.split("\\n");
        Boolean isSemanticValid = true;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.matches("^int\\s+\\w+\\s*=\\s*\\d+;$")) {
                isSemanticValid &= processVariableDeclaration(line, "int");
            } else if (line.matches("^double\\s+\\w+\\s*=\\s*\\d+\\.\\d+;$")) {
                isSemanticValid &= processVariableDeclaration(line, "double");
            } else if (line.matches("^boolean\\s+\\w+\\s*=\\s*(true|false);$")) {
                isSemanticValid &= processVariableDeclaration(line, "boolean");
            } else if (line.matches("^String\\s+\\w+\\s*=\\s*\".*\";$")) {
                isSemanticValid &= processVariableDeclaration(line, "String");
            } else if (line.matches("^char\\s+\\w+\\s*=\\s*'.';$")) {
                isSemanticValid &= processVariableDeclaration(line, "char");
            } else if (line.matches("\\w+\\s*=.*")) {
                isSemanticValid &= processVariableUsage(line);
            }

            if (isSemanticValid) {
                compiler.getResultTextArea().setText("Success: Semantic analysis passed.");
                compiler.getSemanticAnalysisButton().setEnabled(false);
                compiler.setButtonEnabled(compiler.getSemanticAnalysisButton(), false);
            } else {
                compiler.getResultTextArea().setText("Error: Semantic issues detected.");
                compiler.getSemanticAnalysisButton().setEnabled(false);
            }
        }
    }

    private boolean processVariableDeclaration(String line, String type) {

        String variableName = line.split("\\s+")[1];

        if (declaredVariables.contains(variableName)) {
            compiler.getResultTextArea().setText("Error: Variable " + variableName + " already declared.");
            return false;
        }

        declaredVariables.add(variableName);
        variableTypes.put(variableName, type);
        return true;
    }

    private boolean processVariableUsage(String line) {
        String[] tokens = line.split("\\s*=");
        String variableName = tokens[0].trim();

        if (!declaredVariables.contains(variableName)) {
            compiler.getResultTextArea().setText("Error: Variable " + variableName + " not declared.");
            return false;
        }

        String expectedType = variableTypes.get(variableName);

        // Type Checking!
        if (line.matches("^int\\s+\\w+\\s*=\\s*\\d+;$") && !expectedType.equals("int")) {
            compiler.getResultTextArea().setText("Error: Type mismatch for variable " + variableName + ". Expected " + expectedType + " but found int.");
            return false;
        } else if (line.matches("^double\\s+\\w+\\s*=\\s*\\d+\\.\\d+;$") && !expectedType.equals("double")) {
            compiler.getResultTextArea().setText("Error: Type mismatch for variable " + variableName + ". Expected " + expectedType + " but found double.");
            return false;
        } else if (line.matches("^boolean\\s+\\w+\\s*=\\s*(true|false);$") && !expectedType.equals("boolean")) {
            compiler.getResultTextArea().setText("Error: Type mismatch for variable " + variableName + ". Expected " + expectedType + " but found boolean.");
            return false;
        } else if (line.matches("^String\\s+\\w+\\s*=\\s*\".*\";$") && !expectedType.equals("String")) {
            compiler.getResultTextArea().setText("Error: Type mismatch for variable " + variableName + ". Expected " + expectedType + " but found String.");
            return false;
        } else if (line.matches("^char\\s+\\w+\\s*=\\s*'.';$") && !expectedType.equals("char")) {
            compiler.getResultTextArea().setText("Error: Type mismatch for variable " + variableName + ". Expected " + expectedType + " but found char.");
            return false;
        }

        return true;
    }
}





