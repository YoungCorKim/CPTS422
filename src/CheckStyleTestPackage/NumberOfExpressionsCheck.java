package CheckStyleTestPackage;

import com.puppycrawl.tools.checkstyle.api.*;

public class NumberOfExpressionsCheck extends AbstractCheck {

    private static final String EXPRESSION_MSG = "Expression detected.";
    private int expressionCount = 0;

    @Override
    public int[] getDefaultTokens() {
        // EXPR nodes represent expressions in most contexts (statements, for-init/update, returns, etc.)
        return new int[] { TokenTypes.EXPR };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        expressionCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Count each EXPR occurrence
        expressionCount++;
        log(ast.getLineNo(), EXPRESSION_MSG);
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        log(0, "Total number of expressions: " + expressionCount);
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
