package CheckStyleTestPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.HashSet;
import java.util.Set;

public class NumberOfOperandsCheck extends AbstractCheck {

    private static final String OPERAND_MSG = "Operand detected.";
    private int totalOperands = 0;
    private Set<String> uniqueOperands = new HashSet<>();

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.IDENT,              // variable names, method names
            TokenTypes.NUM_INT, TokenTypes.NUM_DOUBLE,
            TokenTypes.NUM_FLOAT, TokenTypes.NUM_LONG,
            TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL,
            TokenTypes.LITERAL_TRUE, TokenTypes.LITERAL_FALSE, TokenTypes.LITERAL_NULL
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        totalOperands = 0;
        uniqueOperands.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        totalOperands++;
        uniqueOperands.add(ast.getText());
        log(ast.getLineNo(), OPERAND_MSG + " (" + ast.getText() + ")");
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        log(0, "Total operands (N2): " + totalOperands);
        log(0, "Unique operands (n2): " + uniqueOperands.size());
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }
}
