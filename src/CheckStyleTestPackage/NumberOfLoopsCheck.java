package CheckStyleTestPackage;
import com.puppycrawl.tools.checkstyle.api.*;

public class NumberOfLoopsCheck extends AbstractCheck
{



    private static final String LOOP_MSG = "Loop statement detected: ";
    private int loopCount = 0;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        loopCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        loopCount++;
        log(ast.getLineNo(), LOOP_MSG + ast.getText());
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        log(0, "Total number of loops: " + loopCount);
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
