package CheckStyleTestPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.HashSet;
import java.util.Set;

public class HalsteadVolumeCheck extends AbstractCheck {

    private static final String LOG_PREFIX = "Halstead Volume Metric: ";
    private int totalOperators = 0;
    private int totalOperands = 0;
    private Set<String> uniqueOperators = new HashSet<>();
    private Set<String> uniqueOperands = new HashSet<>();

    private static final int[] OPERATOR_TOKENS = new int[] {
        TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.STAR, TokenTypes.DIV, TokenTypes.MOD,
        TokenTypes.LT, TokenTypes.GT, TokenTypes.LE, TokenTypes.GE, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
        TokenTypes.LAND, TokenTypes.LOR, TokenTypes.LNOT,
        TokenTypes.ASSIGN, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN,
        TokenTypes.STAR_ASSIGN, TokenTypes.DIV_ASSIGN, TokenTypes.MOD_ASSIGN,
        TokenTypes.INC, TokenTypes.DEC,
        TokenTypes.LITERAL_IF, TokenTypes.LITERAL_ELSE, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_DO, TokenTypes.LITERAL_SWITCH, TokenTypes.LITERAL_RETURN
    };

    private static final int[] OPERAND_TOKENS = new int[] {
        TokenTypes.IDENT, TokenTypes.NUM_INT, TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT,
        TokenTypes.NUM_LONG, TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL,
        TokenTypes.LITERAL_TRUE, TokenTypes.LITERAL_FALSE, TokenTypes.LITERAL_NULL
    };

    @Override
    public int[] getDefaultTokens() {
        int[] all = new int[OPERATOR_TOKENS.length + OPERAND_TOKENS.length];
        System.arraycopy(OPERATOR_TOKENS, 0, all, 0, OPERATOR_TOKENS.length);
        System.arraycopy(OPERAND_TOKENS, 0, all, OPERATOR_TOKENS.length, OPERAND_TOKENS.length);
        return all;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        totalOperators = 0;
        totalOperands = 0;
        uniqueOperators.clear();
        uniqueOperands.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        int type = ast.getType();
        if (isOperator(type)) {
            totalOperators++;
            uniqueOperators.add(ast.getText());
        } else if (isOperand(type)) {
            totalOperands++;
            uniqueOperands.add(ast.getText());
        }
    }

    private boolean isOperator(int tokenType) {
        for (int t : OPERATOR_TOKENS) if (t == tokenType) return true;
        return false;
    }

    private boolean isOperand(int tokenType) {
        for (int t : OPERAND_TOKENS) if (t == tokenType) return true;
        return false;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        int N = totalOperators + totalOperands;
        int n = uniqueOperators.size() + uniqueOperands.size();

        double volume = (n > 1) ? N * (Math.log(n) / Math.log(2)) : 0.0;

        log(0, LOG_PREFIX + "Total Operators (N1): " + totalOperators);
        log(0, LOG_PREFIX + "Total Operands (N2): " + totalOperands);
        log(0, LOG_PREFIX + "Unique Operators (n1): " + uniqueOperators.size());
        log(0, LOG_PREFIX + "Unique Operands (n2): " + uniqueOperands.size());
        log(0, LOG_PREFIX + "Program Vocabulary (n = n1 + n2): " + n);
        log(0, LOG_PREFIX + "Program Length (N = N1 + N2): " + N);
        log(0, LOG_PREFIX + String.format("Halstead Volume (V = N × log2(n)): %.2f", volume));
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
