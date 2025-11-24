package CheckStyleTestPackage;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.*;

class ExpressionCheckTest {

    @Test
    public void testGetDefaultTokens() {
        NumberOfExpressionsCheck check = new NumberOfExpressionsCheck();
        int[] expected = { TokenTypes.EXPR };

        assertArrayEquals(expected, check.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        NumberOfExpressionsCheck check = new NumberOfExpressionsCheck();
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        NumberOfExpressionsCheck check = new NumberOfExpressionsCheck();
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    public void testBeginTreeResetsCount() {
        NumberOfExpressionsCheck check = new NumberOfExpressionsCheck();
        DetailAST root = mock(DetailAST.class);

        check.beginTree(root); // ensure it runs without exception
    }

    @Test
    public void testVisitTokenLogsExpression() {
        NumberOfExpressionsCheck spyCheck = spy(new NumberOfExpressionsCheck());
        DetailAST ast = mock(DetailAST.class);

        when(ast.getLineNo()).thenReturn(12);
        doNothing().when(spyCheck).log(anyInt(), anyString());

        spyCheck.visitToken(ast);

        verify(spyCheck).log(12, "Expression detected.");
    }

    @Test
    public void testFinishTreeLogsTotalExpressions() {
        NumberOfExpressionsCheck spyCheck = spy(new NumberOfExpressionsCheck());
        DetailAST root = mock(DetailAST.class);
        DetailAST expr = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());
        when(expr.getLineNo()).thenReturn(5);

        spyCheck.beginTree(root);

        // simulate 3 EXPR tokens
        spyCheck.visitToken(expr);
        spyCheck.visitToken(expr);
        spyCheck.visitToken(expr);

        spyCheck.finishTree(root);

        verify(spyCheck).log(0, "Total number of expressions: 3");
    }
}
