package CheckStyleTestPackage;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.*;

class CommentsCheckTest {

    @Test
    public void testIsCommentNodesRequired() {
        NumberOfCommentsCheck check = new NumberOfCommentsCheck();
        assertEquals(true, check.isCommentNodesRequired());
    }

    @Test
    public void testGetDefaultTokens() {
        NumberOfCommentsCheck check = new NumberOfCommentsCheck();
        int[] expectedTokens = {
                TokenTypes.SINGLE_LINE_COMMENT,
                TokenTypes.BLOCK_COMMENT_BEGIN
        };

        assertArrayEquals(expectedTokens, check.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        NumberOfCommentsCheck check = new NumberOfCommentsCheck();
        int[] expectedTokens = {
                TokenTypes.SINGLE_LINE_COMMENT,
                TokenTypes.BLOCK_COMMENT_BEGIN
        };

        assertArrayEquals(expectedTokens, check.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        NumberOfCommentsCheck check = new NumberOfCommentsCheck();
        int[] expected = new int[0];

        assertArrayEquals(expected, check.getRequiredTokens());
    }

    @Test
    public void testBeginTree() {
        NumberOfCommentsCheck check = new NumberOfCommentsCheck();
        DetailAST root = mock(DetailAST.class);

        // Just ensure no exception is thrown and internal state is reset
        check.beginTree(root);
    }

    @Test
    public void testVisitTokenLogsComment() {
        NumberOfCommentsCheck spyCheck = spy(new NumberOfCommentsCheck());
        DetailAST ast = mock(DetailAST.class);

        when(ast.getLineNo()).thenReturn(10);
        doNothing().when(spyCheck).log(anyInt(), anyString());

        spyCheck.visitToken(ast);

        verify(spyCheck, times(1)).log(10, "Comment detected.");
    }

    @Test
    public void testFinishTreeLogsTotalNumberOfComments() {
        NumberOfCommentsCheck spyCheck = spy(new NumberOfCommentsCheck());
        DetailAST ast = mock(DetailAST.class);

        when(ast.getLineNo()).thenReturn(5);
        doNothing().when(spyCheck).log(anyInt(), anyString());

        // Reset counter and simulate two comments
        spyCheck.beginTree(ast);
        spyCheck.visitToken(ast);
        spyCheck.visitToken(ast);

        spyCheck.finishTree(ast);

        verify(spyCheck).log(0, "Total number of comments: 2");
    }
}
