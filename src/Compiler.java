import javax.swing.*;
import java.awt.*;


public class Compiler extends JFrame {
    private JTextArea codeTextArea;
    private JTextArea resultTextArea;
    private JButton openFileButton;
    private JButton lexicalAnalysisButton;
    private JButton syntaxAnalysisButton;
    private JButton semanticAnalysisButton;
    private JButton clearButton;


    public Compiler() {
        setTitle("Final Project Compiler");
        setSize(700, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));


        Color darkGreen = new Color(0x013220);
        Color mediumGreen = new Color(0x014421);
        Color lightGreen = new Color(0x355E3B);
        Color oliveGreen = new Color(0x8A9A5B);
        Color beige = new Color(0xBCB88A);


        getContentPane().setBackground(lightGreen);


        codeTextArea = new JTextArea();
        resultTextArea = new JTextArea();
        codeTextArea.setBorder(BorderFactory.createTitledBorder("Code Text Area"));
        resultTextArea.setBorder(BorderFactory.createTitledBorder("Result Text Area"));


        codeTextArea.setBackground(beige);
        codeTextArea.setForeground(darkGreen);
        resultTextArea.setBackground(beige);
        resultTextArea.setForeground(darkGreen);


        resultTextArea.setEditable(false);


        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 10));
        buttonPanel.setBackground(lightGreen);


        openFileButton = new JButton("Open File");
        lexicalAnalysisButton = new JButton("Lexical Analysis");
        syntaxAnalysisButton = new JButton("Syntax Analysis");
        semanticAnalysisButton = new JButton("Semantic Analysis");
        clearButton = new JButton("Clear Text");


        openFileButton.setBackground(mediumGreen);
        openFileButton.setForeground(Color.WHITE);
        openFileButton.setFocusPainted(false);


        lexicalAnalysisButton.setBackground(mediumGreen);
        lexicalAnalysisButton.setForeground(Color.WHITE);
        lexicalAnalysisButton.setFocusPainted(false);


        syntaxAnalysisButton.setBackground(mediumGreen);
        syntaxAnalysisButton.setForeground(Color.WHITE);
        syntaxAnalysisButton.setFocusPainted(false);


        semanticAnalysisButton.setBackground(mediumGreen);
        semanticAnalysisButton.setForeground(Color.WHITE);
        semanticAnalysisButton.setFocusPainted(false);


        clearButton.setBackground(mediumGreen);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);


        Insets buttonPadding = new Insets(5, 10, 5, 10);
        openFileButton.setMargin(buttonPadding);
        lexicalAnalysisButton.setMargin(buttonPadding);
        syntaxAnalysisButton.setMargin(buttonPadding);
        semanticAnalysisButton.setMargin(buttonPadding);
        clearButton.setMargin(buttonPadding);


        lexicalAnalysisButton.setEnabled(false);
        syntaxAnalysisButton.setEnabled(false);
        semanticAnalysisButton.setEnabled(false);
        clearButton.setEnabled(false);


        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));


        buttonPanel.add(openFileButton);
        buttonPanel.add(lexicalAnalysisButton);
        buttonPanel.add(syntaxAnalysisButton);
        buttonPanel.add(semanticAnalysisButton);
        buttonPanel.add(clearButton);


        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(oliveGreen);


        mainPanel.add(new JScrollPane(resultTextArea), BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(codeTextArea), BorderLayout.CENTER);


        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);


        // Set button actions using separate classes
        openFileButton.addActionListener(new OpenFileAction(this));
        lexicalAnalysisButton.addActionListener(new LexicalAnalysisAction(this));
        clearButton.addActionListener(new ClearTextAction(this));
        syntaxAnalysisButton.addActionListener(new SyntaxAnalysisAction(this));
        semanticAnalysisButton.addActionListener(new SemanticAnalysisAction(this));
    }


    public JTextArea getCodeTextArea() {
        return codeTextArea;
    }


    public JTextArea getResultTextArea() {
        return resultTextArea;
    }


    public JButton getOpenFileButton() {
        return openFileButton;
    }


    public JButton getLexicalAnalysisButton() {
        return lexicalAnalysisButton;
    }


    public JButton getSyntaxAnalysisButton() {
        return syntaxAnalysisButton;
    }


    public JButton getSemanticAnalysisButton() {
        return semanticAnalysisButton;
    }


    public JButton getClearButton() {
        return clearButton;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Compiler ui = new Compiler();
            ui.setVisible(true);
        });
    }
}

