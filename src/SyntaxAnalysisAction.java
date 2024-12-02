import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SyntaxAnalysisAction implements ActionListener {
    private Compiler compiler;


    public SyntaxAnalysisAction(Compiler compiler) {
        this.compiler = compiler;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        performSyntaxAnalysis();
    }


    private void performSyntaxAnalysis() {
        String code = compiler.getCodeTextArea().getText();
        String[] lines = code.split("\\n");


        boolean isSyntaxValid = true;
        int openParenthesesCount = 0;
        int openBracesCount = 0;
        int openQuotesCount = 0;


        for (String line : lines) {
            line = line.trim();


            if (!line.isEmpty() && !line.endsWith(";") && !line.equals("}") && !line.equals("{")) {
                isSyntaxValid = false;
                break;
            }


            openParenthesesCount += countOccurrences(line, '(');
            openParenthesesCount -= countOccurrences(line, ')');


            openBracesCount += countOccurrences(line, '{');
            openBracesCount -= countOccurrences(line, '}');


            openQuotesCount += countOccurrences(line, '"');


            if (openParenthesesCount < 0 || openBracesCount < 0 || openQuotesCount % 2 != 0) {
                isSyntaxValid = false;
                break;
            }
        }


        if (openParenthesesCount != 0 || openBracesCount != 0 || openQuotesCount % 2 != 0) {
            isSyntaxValid = false;
        }


        if (isSyntaxValid) {
            compiler.getResultTextArea().setText("Success: Syntax is valid.");
            compiler.getSyntaxAnalysisButton().setEnabled(false);
            compiler.getSemanticAnalysisButton().setEnabled(true);
        } else {
            compiler.getResultTextArea().setText("Error: Syntax is invalid.");
            compiler.getSyntaxAnalysisButton().setEnabled(false);
            compiler.getSemanticAnalysisButton().setEnabled(false);
        }
    }


    private int countOccurrences(String line, char character) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == character) {
                count++;
            }
        }
        return count;
    }
}
