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
        boolean isSemanticValid = true;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Match variable declarations
            if (line.matches("^\\s*(int|double|boolean|String|char)\\s+\\w+\\s*=\\s*[^;]+;\\s*$")) {
                isSemanticValid &= processVariableDeclaration(line);
            }
            // Match variable usage (assignments)
            else if (line.matches("\\w+\\s*=\\s*.*")) {
                isSemanticValid &= processVariableUsage(line);
            }
        }

        if (isSemanticValid) {
            compiler.getResultTextArea().append("\nSuccess: Semantic analysis passed.");
        } else {
            compiler.getResultTextArea().append("\nError: Semantic issues detected.");
        }
        compiler.getSemanticAnalysisButton().setEnabled(false);
    }

    private boolean processVariableDeclaration(String line) {
        String[] tokens = line.split("\\s+");
        String variableName = tokens[1];

        if (declaredVariables.contains(variableName)) {
            compiler.getResultTextArea().append("\nError: Variable " + variableName + " already declared.");
            return false;
        }

        declaredVariables.add(variableName);
        variableTypes.put(variableName, tokens[0]); // Type (int, double, etc.)
        return true;
    }

    private boolean processVariableUsage(String line) {
        String[] tokens = line.split("\\s*=");
        String variableName = tokens[0].trim();
        String value = tokens[1].trim();

        if (!declaredVariables.contains(variableName)) {
            compiler.getResultTextArea().append("\nError: Variable " + variableName + " not declared.");
            return false;
        }

        String declaredType = variableTypes.get(variableName);

        // Check type mismatch
        if (!checkTypeCompatibility(declaredType, value)) {
            compiler.getResultTextArea().append("\nError: Type mismatch for variable " + variableName);
            return false;
        }

        return true;
    }

    private boolean checkTypeCompatibility(String declaredType, String value) {
        // Clean up the value by removing semicolons and trimming extra spaces
        value = value.replaceAll(";", "").trim();  // Remove semicolons and extra spaces

        // Debugging line to print declared type and assigned value
        System.out.println("Checking value: \"" + value + "\" (Declared type: " + declaredType + ")");

        // Check for compatibility between declared type and assigned value
        if (declaredType.equals("int")) {
            return value.matches("\\d+"); // Integer check (only digits)
        } else if (declaredType.equals("double")) {
            return value.matches("\\d+(\\.\\d+)?"); // Double check (digits or floating point)
        } else if (declaredType.equals("boolean")) {
            return value.equals("true") || value.equals("false"); // Boolean check (true or false)
        } else if (declaredType.equals("String")) {
            return value.startsWith("\"") && value.endsWith("\""); // String check (must be enclosed in double quotes)
        } else if (declaredType.equals("char")) {
            // Check if the value is a single character enclosed in single quotes
            if (value.length() == 3 && value.startsWith("'") && value.endsWith("'")) {
                // Extract the character (strip the surrounding quotes)
                String charValue = value.substring(1, value.length() - 1);
                return charValue.length() == 1; // Ensure it's exactly one character
            }
        }
        return false;
    }


}
