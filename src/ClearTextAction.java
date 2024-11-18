package TPLProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClearTextAction implements ActionListener {
    private final Compiler compiler;


    public ClearTextAction(Compiler compiler) {
        this.compiler = compiler;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        compiler.getCodeTextArea().setText("");
        compiler.getResultTextArea().setText("");


        compiler.getOpenFileButton().setEnabled(true);
        compiler.setButtonEnabled(compiler.getOpenFileButton(), true);
        compiler.getLexicalAnalysisButton().setEnabled(false);
        compiler.setButtonEnabled(compiler.getLexicalAnalysisButton(), false);
        compiler.getSyntaxAnalysisButton().setEnabled(false);
        compiler.setButtonEnabled(compiler.getSyntaxAnalysisButton(), false);
        compiler.getSemanticAnalysisButton().setEnabled(false);
        compiler.setButtonEnabled(compiler.getSemanticAnalysisButton(), false);
        compiler.getClearButton().setEnabled(false);

    }
}

