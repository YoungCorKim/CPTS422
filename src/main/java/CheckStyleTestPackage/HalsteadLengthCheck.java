package CheckStyleTestPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.HashSet;
import java.util.Set;

public class HalsteadLengthCheck extends AbstractCheck {

    private static final String LOG_PREFIX = "Halstead Length Metric: ";
    private int totalOperators = 0;
    private int totalOperands = 0;

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
    }

    @Override
    public void visitToken(DetailAST ast) {
        int type = ast.getType();
        for (int op : OPERATOR_TOKENS) {
            if (type == op) {
                totalOperators++;
                return;
            }
        }
        for (int opd : OPERAND_TOKENS) {
            if (type == opd) {
                totalOperands++;
                return;
            }
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        int halsteadLength = totalOperators + totalOperands;
        log(0, LOG_PREFIX + "Total Operators (N1): " + totalOperators);
        log(0, LOG_PREFIX + "Total Operands (N2): " + totalOperands);
        log(0, LOG_PREFIX + "Program Length (N = N1 + N2): " + halsteadLength);
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
