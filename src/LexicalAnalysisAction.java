import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.*;

public class LexicalAnalysisAction implements ActionListener {

    private final Compiler compiler;

    // Constructor that accepts the Compiler instance
    public LexicalAnalysisAction(Compiler compiler) {
        this.compiler = compiler;
    }

    // Token types and regex patterns
    private static final Map<String, String> TOKEN_PATTERNS = new LinkedHashMap<>();
    static {
        TOKEN_PATTERNS.put("KEYWORD", "\\b(int|double|boolean|String|char|if|else|for|while|return)\\b");
        TOKEN_PATTERNS.put("IDENTIFIER", "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b");
        TOKEN_PATTERNS.put("NUMBER", "\\b\\d+(\\.\\d+)?\\b");
        TOKEN_PATTERNS.put("BOOLEAN", "\\b(true|false)\\b");
        TOKEN_PATTERNS.put("CHARACTER", "'[a-zA-Z]'"); // Match single characters like 'A'
        TOKEN_PATTERNS.put("STRING", "\"[^\"]*\""); // Match strings enclosed in quotes
        TOKEN_PATTERNS.put("ASSIGNMENT_OPERATOR", "=");
        TOKEN_PATTERNS.put("SEMICOLON", ";");
        TOKEN_PATTERNS.put("WHITESPACE", "\\s+");
        TOKEN_PATTERNS.put("OPERATOR", "[+\\-*/]");
        TOKEN_PATTERNS.put("PARENTHESIS", "[()]");
        TOKEN_PATTERNS.put("BRACE", "[{}]");
    }

    // Token class
    public static class Token {
        public String type;
        public String value;

        public Token(String type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("Token[type=%s, value='%s']", type, value);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Retrieve the source code from the codeTextArea

            JTextArea codeArea = compiler.getCodeTextArea();
            String sourceCode = codeArea.getText();
            System.out.println(codeArea);

            if (sourceCode.isEmpty()) {
                JOptionPane.showMessageDialog(compiler, "No source code provided!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }



            // Perform lexical analysis
            List<Token> tokens = performLexicalAnalysis(sourceCode);


            // Display tokens in the resultTextArea
            JTextArea resultArea = compiler.getResultTextArea();
            StringBuilder resultText = new StringBuilder();
            for (Token token : tokens) {
                resultText.append(token).append("\n");
            }
            resultArea.setText(resultText.toString());

            // Enable the next analysis button
            compiler.getLexicalAnalysisButton().setEnabled(false);
            compiler.getSyntaxAnalysisButton().setEnabled(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(compiler, "Error during lexical analysis: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Token> performLexicalAnalysis(String sourceCode) throws Exception {
        List<Token> tokens = new ArrayList<>();
        int position = 0;

        while (position < sourceCode.length()) {
            boolean matchFound = false;

            for (Map.Entry<String, String> entry : TOKEN_PATTERNS.entrySet()) {
                String tokenType = entry.getKey();
                String pattern = entry.getValue();
                Pattern regex = Pattern.compile(pattern);
                Matcher matcher = regex.matcher(sourceCode.substring(position));

                if (matcher.find() && matcher.start() == 0) {
                    String value = matcher.group();
                    // Debugging: Print matched token type and value
                    System.out.println("Matched: " + tokenType + " -> " + value);

                    // Skip whitespace tokens but advance position
                    if (!tokenType.equals("WHITESPACE")) {
                        tokens.add(new Token(tokenType, value));
                    }

                    position += value.length();
                    matchFound = true;
                    break;
                }
            }

            if (!matchFound) {
                System.err.println("Unrecognized token at position: " + position);
                throw new Exception("Unrecognized token at position " + position);
            }
        }

        return tokens;
    }


}
