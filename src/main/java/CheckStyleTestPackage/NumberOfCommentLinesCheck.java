package CheckStyleTestPackage;

import com.puppycrawl.tools.checkstyle.api.*;

public class NumberOfCommentLinesCheck extends AbstractCheck {

    private static final String COMMENT_LINE_MSG = "Comment line detected.";
    private int commentLineCount = 0;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN
        };
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        commentLineCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            // Each single-line comment is one line
            commentLineCount++;
            log(ast.getLineNo(), COMMENT_LINE_MSG);
        } else if (ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            // Count how many lines the block comment spans
            DetailAST end = ast.findFirstToken(TokenTypes.BLOCK_COMMENT_END);
            if (end != null) {
                int startLine = ast.getLineNo();
                int endLine = end.getLineNo();
                int linesInBlock = endLine - startLine + 1;
                commentLineCount += linesInBlock;
                log(startLine, COMMENT_LINE_MSG + " (block comment: " + linesInBlock + " lines)");
            } else {
                // fallback: if end not found, assume one line
                commentLineCount++;
                log(ast.getLineNo(), COMMENT_LINE_MSG + " (block comment)");
            }
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        log(0, "Total number of comment lines: " + commentLineCount);
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
