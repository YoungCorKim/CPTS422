package CheckStyleTestPackage;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.*;

class LoopCheckTest {

    @Test
    public void testGetDefaultTokens() {
        NumberOfLoopsCheck check = new NumberOfLoopsCheck();
        int[] expected = {
                TokenTypes.LITERAL_FOR,
                TokenTypes.LITERAL_WHILE,
                TokenTypes.LITERAL_DO
        };

        assertArrayEquals(expected, check.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        NumberOfLoopsCheck check = new NumberOfLoopsCheck();
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        NumberOfLoopsCheck check = new NumberOfLoopsCheck();
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    public void testBeginTreeResetsCount() {
        NumberOfLoopsCheck check = new NumberOfLoopsCheck();
        DetailAST root = mock(DetailAST.class);

        // Should simply reset internal state with no errors
        check.beginTree(root);
    }

    @Test
    public void testVisitTokenIncrementsAndLogs() {
        NumberOfLoopsCheck spyCheck = spy(new NumberOfLoopsCheck());
        DetailAST ast = mock(DetailAST.class);

        when(ast.getLineNo()).thenReturn(12);
        when(ast.getText()).thenReturn("for");
        doNothing().when(spyCheck).log(anyInt(), anyString());

        spyCheck.visitToken(ast);

        verify(spyCheck).log(12, "Loop statement detected: for");
    }

    @Test
    public void testFinishTreeLogsTotalLoops() {
        NumberOfLoopsCheck spyCheck = spy(new NumberOfLoopsCheck());
        DetailAST root = mock(DetailAST.class);
        DetailAST ast = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        when(ast.getLineNo()).thenReturn(5);
        when(ast.getText()).thenReturn("while");
        when(root.getLineNo()).thenReturn(100);

        // beginTree
        spyCheck.beginTree(root);

        // simulate 3 loop tokens
        spyCheck.visitToken(ast);
        spyCheck.visitToken(ast);
        spyCheck.visitToken(ast);

        spyCheck.finishTree(root);

        verify(spyCheck).log(100, "Total number of loops: 3");
    }
}
