
package CheckStyleTestPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.*;

class CommentLinesCheckTest {

    @Test
    public void testIsCommentNodesRequired() {
        NumberOfCommentLinesCheck check = new NumberOfCommentLinesCheck();
        assertTrue(check.isCommentNodesRequired());
    }

    @Test
    public void testGetDefaultTokens() {
        NumberOfCommentLinesCheck check = new NumberOfCommentLinesCheck();
        int[] expected = {
                TokenTypes.SINGLE_LINE_COMMENT,
                TokenTypes.BLOCK_COMMENT_BEGIN
        };

        assertArrayEquals(expected, check.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        NumberOfCommentLinesCheck check = new NumberOfCommentLinesCheck();
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        NumberOfCommentLinesCheck check = new NumberOfCommentLinesCheck();
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    public void testBeginTreeResetsCounter() {
        NumberOfCommentLinesCheck check = new NumberOfCommentLinesCheck();
        DetailAST root = mock(DetailAST.class);

        check.beginTree(root); // Should not throw
    }

    @Test
    public void testVisitTokenSingleLineComment() {
        NumberOfCommentLinesCheck spyCheck = spy(new NumberOfCommentLinesCheck());
        DetailAST ast = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());
        when(ast.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
        when(ast.getLineNo()).thenReturn(7);

        spyCheck.visitToken(ast);

        verify(spyCheck, times(1)).log(7, "Comment line detected.");
    }

    @Test
    public void testVisitTokenBlockComment() {
        NumberOfCommentLinesCheck spyCheck = spy(new NumberOfCommentLinesCheck());

        DetailAST begin = mock(DetailAST.class);
        DetailAST end = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        when(begin.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);
        when(begin.getLineNo()).thenReturn(10);

        when(end.getLineNo()).thenReturn(13);
        when(begin.findFirstToken(TokenTypes.BLOCK_COMMENT_END))
                .thenReturn(end);

        spyCheck.visitToken(begin);

        // Block spans lines 10 → 13 = 4 lines
        verify(spyCheck).log(10, "Comment line detected. (block comment: 4 lines)");
    }

    @Test
    public void testVisitTokenBlockCommentWithoutEnd() {
        NumberOfCommentLinesCheck spyCheck = spy(new NumberOfCommentLinesCheck());
        DetailAST begin = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        when(begin.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);
        when(begin.getLineNo()).thenReturn(21);
        when(begin.findFirstToken(TokenTypes.BLOCK_COMMENT_END))
                .thenReturn(null);  // No end found

        spyCheck.visitToken(begin);

        verify(spyCheck).log(21, "Comment line detected. (block comment)");
    }

    @Test
    public void testFinishTreeLogsTotal() {
        NumberOfCommentLinesCheck spyCheck = spy(new NumberOfCommentLinesCheck());
        DetailAST ast = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        // Simulate counting
        spyCheck.beginTree(ast);
        when(ast.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
        when(ast.getLineNo()).thenReturn(1);

        spyCheck.visitToken(ast);
        spyCheck.visitToken(ast);
        spyCheck.visitToken(ast);

        spyCheck.finishTree(ast);

        verify(spyCheck).log(0, "Total number of comment lines: 3");
    }
}
