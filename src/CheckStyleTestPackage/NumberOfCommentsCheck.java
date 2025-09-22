package CheckStyleTestPackage;
import com.puppycrawl.tools.checkstyle.api.*;



public class NumberOfCommentsCheck extends AbstractCheck {
    private static final String COMMENT_MSG = "Comment detected.";
    private int commentCount = 0;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN
        };
    }
    @Override
    public boolean isCommentNodesRequired(){
    	return true;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        commentCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        commentCount++;
        log(ast.getLineNo(), COMMENT_MSG);
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        log(0, "Total number of comments: " + commentCount);
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

