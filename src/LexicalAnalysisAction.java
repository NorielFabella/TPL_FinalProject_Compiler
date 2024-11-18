package TPLProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LexicalAnalysisAction implements ActionListener {
    private final Compiler compiler;


    public LexicalAnalysisAction(Compiler compiler) {
        this.compiler = compiler;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String code = compiler.getCodeTextArea().getText();
        String[] lines = code.split("\\n");
        boolean isValid = true;


        String intPattern = "^int\\s+\\w+\\s*=\\s*\\d+;$";
        String doublePattern = "^double\\s+\\w+\\s*=\\s*\\d+\\.\\d+;$";
        String booleanPattern = "^boolean\\s+\\w+\\s*=\\s*(true|false);$";
        String stringPattern = "^String\\s+\\w+\\s*=\\s*\".*\";$";
        String charPattern = "^char\\s+\\w+\\s*=\\s*'.';$";


        for (String line : lines) {
            line = line.trim();
            if (!line.matches(intPattern) && !line.matches(doublePattern) &&
                    !line.matches(booleanPattern) && !line.matches(stringPattern) &&
                    !line.matches(charPattern)) {
                isValid = false;
                break;
            }
        }


        if (isValid) {
            compiler.getResultTextArea().setText("Success: All declarations are valid.");
            compiler.getLexicalAnalysisButton().setEnabled(false);
            compiler.setButtonEnabled(compiler.getLexicalAnalysisButton(), false);
            compiler.getSyntaxAnalysisButton().setEnabled(true);
            compiler.setButtonEnabled(compiler.getSyntaxAnalysisButton(), true);
        } else {
            compiler.getResultTextArea().setText("Error: Invalid declaration found.");
            compiler.getSyntaxAnalysisButton().setEnabled(false);

        }

    }
}

