package CheckStyleTestPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.HashSet;
import java.util.Set;

public class NumberOfOperatorsCheck extends AbstractCheck {

    private static final String OPERATOR_MSG = "Operator detected.";
    private int totalOperators = 0;
    private Set<String> uniqueOperators = new HashSet<>();

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            // Arithmetic operators
            TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.STAR, TokenTypes.DIV, TokenTypes.MOD,
            // Relational operators
            TokenTypes.LT, TokenTypes.GT, TokenTypes.LE, TokenTypes.GE, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
            // Logical operators
            TokenTypes.LAND, TokenTypes.LOR, TokenTypes.LNOT,
            // Bitwise operators
            TokenTypes.BAND, TokenTypes.BOR, TokenTypes.BXOR, TokenTypes.BNOT, TokenTypes.SL, TokenTypes.SR, TokenTypes.BSR,
            // Assignment operators
            TokenTypes.ASSIGN, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN,
            TokenTypes.STAR_ASSIGN, TokenTypes.DIV_ASSIGN, TokenTypes.MOD_ASSIGN,
            // Increment/decrement
            TokenTypes.INC, TokenTypes.DEC,
            // Control structures (treated as operators)
            TokenTypes.LITERAL_IF, TokenTypes.LITERAL_ELSE, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_DO, TokenTypes.LITERAL_SWITCH, TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_RETURN
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        totalOperators = 0;
        uniqueOperators.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        totalOperators++;
        uniqueOperators.add(ast.getText());
        log(ast.getLineNo(), OPERATOR_MSG + " (" + ast.getText() + ")");
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        log(0, "Total operators (N1): " + totalOperators);
        log(0, "Unique operators (n1): " + uniqueOperators.size());
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
