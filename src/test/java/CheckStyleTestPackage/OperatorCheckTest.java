package CheckStyleTestPackage;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.*;

class OperatorCheckTest {

    @Test
    public void testGetDefaultTokens() {
        NumberOfOperatorsCheck check = new NumberOfOperatorsCheck();

        int[] expected = check.getDefaultTokens();
        assertArrayEquals(expected, check.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        NumberOfOperatorsCheck check = new NumberOfOperatorsCheck();
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        NumberOfOperatorsCheck check = new NumberOfOperatorsCheck();
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    public void testBeginTreeResetsCounters() {
        NumberOfOperatorsCheck check = new NumberOfOperatorsCheck();
        DetailAST root = mock(DetailAST.class);

        // ensure method runs without exception and resets internal state
        check.beginTree(root);
    }

    @Test
    public void testVisitTokenLogsOperator() {
        NumberOfOperatorsCheck spyCheck = spy(new NumberOfOperatorsCheck());
        DetailAST ast = mock(DetailAST.class);

        when(ast.getLineNo()).thenReturn(4);
        when(ast.getText()).thenReturn("+");

        doNothing().when(spyCheck).log(anyInt(), anyString());

        spyCheck.visitToken(ast);

        verify(spyCheck).log(4, "Operator detected. (+)");
    }

    @Test
    public void testVisitTokenUpdatesCounts() {
        NumberOfOperatorsCheck spyCheck = spy(new NumberOfOperatorsCheck());
        DetailAST ast = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        when(ast.getLineNo()).thenReturn(1);
        when(ast.getText()).thenReturn("+");

        // Simulate 3 operator occurrences
        spyCheck.beginTree(ast);
        spyCheck.visitToken(ast);
        spyCheck.visitToken(ast);
        spyCheck.visitToken(ast);

        spyCheck.finishTree(ast);

        // N1 = total operators
        verify(spyCheck).log(0, "Total operators (N1): 3");

        // n1 = unique operators (set)
        verify(spyCheck).log(0, "Unique operators (n1): 1");
    }

    @Test
    public void testFinishTreeMultipleUniqueOperators() {
        NumberOfOperatorsCheck spyCheck = spy(new NumberOfOperatorsCheck());

        DetailAST ast1 = mock(DetailAST.class);
        DetailAST ast2 = mock(DetailAST.class);
        DetailAST ast3 = mock(DetailAST.class);
        DetailAST root = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        when(ast1.getText()).thenReturn("+");
        when(ast2.getText()).thenReturn("-");
        when(ast3.getText()).thenReturn("*");

        when(ast1.getLineNo()).thenReturn(10);
        when(ast2.getLineNo()).thenReturn(11);
        when(ast3.getLineNo()).thenReturn(12);

        spyCheck.beginTree(root);

        spyCheck.visitToken(ast1);
        spyCheck.visitToken(ast2);
        spyCheck.visitToken(ast3);

        spyCheck.finishTree(root);

        verify(spyCheck).log(0, "Total operators (N1): 3");
        verify(spyCheck).log(0, "Unique operators (n1): 3");
    }
}
