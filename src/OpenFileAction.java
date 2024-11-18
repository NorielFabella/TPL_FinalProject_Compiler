package TPLProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class OpenFileAction implements ActionListener {
    private final Compiler compiler;


    public OpenFileAction(Compiler compiler) {
        this.compiler = compiler;

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Java Files", "java"));
        int returnValue = fileChooser.showOpenDialog(compiler);


        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();


            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder fileContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
                compiler.getCodeTextArea().setText(fileContent.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            compiler.getOpenFileButton().setEnabled(false);
            compiler.setButtonEnabled(compiler.getOpenFileButton(), false);
            compiler.getLexicalAnalysisButton().setEnabled(true);
            compiler.setButtonEnabled(compiler.getLexicalAnalysisButton(), true);
            compiler.setButtonEnabled(compiler.getClearButton(), true);
            compiler.getSyntaxAnalysisButton().setEnabled(false);
            compiler.getSemanticAnalysisButton().setEnabled(false);
            compiler.getClearButton().setEnabled(true);
        }
    }
}

