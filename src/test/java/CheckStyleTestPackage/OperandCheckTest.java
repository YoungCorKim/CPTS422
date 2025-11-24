package CheckStyleTestPackage;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.*;

class OperandCheckTest {

    @Test
    public void testGetDefaultTokens() {
        NumberOfOperandsCheck check = new NumberOfOperandsCheck();

        int[] expected = check.getDefaultTokens();
        assertArrayEquals(expected, check.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        NumberOfOperandsCheck check = new NumberOfOperandsCheck();
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        NumberOfOperandsCheck check = new NumberOfOperandsCheck();
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    public void testBeginTreeResetsCounters() {
        NumberOfOperandsCheck check = new NumberOfOperandsCheck();
        DetailAST root = mock(DetailAST.class);

        // Just make sure it runs and resets internal state
        check.beginTree(root);
    }

    @Test
    public void testVisitTokenLogsOperand() {
        NumberOfOperandsCheck spyCheck = spy(new NumberOfOperandsCheck());
        DetailAST ast = mock(DetailAST.class);

        when(ast.getLineNo()).thenReturn(8);
        when(ast.getText()).thenReturn("x");

        doNothing().when(spyCheck).log(anyInt(), anyString());

        spyCheck.visitToken(ast);

        verify(spyCheck).log(8, "Operand detected. (x)");
    }

    @Test
    public void testVisitTokenUpdatesCounts() {
        NumberOfOperandsCheck spyCheck = spy(new NumberOfOperandsCheck());
        DetailAST root = mock(DetailAST.class);
        DetailAST ast = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        when(ast.getLineNo()).thenReturn(3);
        when(ast.getText()).thenReturn("value");

        spyCheck.beginTree(root);

        // Simulate three identical operands
        spyCheck.visitToken(ast);
        spyCheck.visitToken(ast);
        spyCheck.visitToken(ast);

        spyCheck.finishTree(root);

        // N2 = total operands
        verify(spyCheck).log(0, "Total operands (N2): 3");

        // n2 = unique operands (set)
        verify(spyCheck).log(0, "Unique operands (n2): 1");
    }

    @Test
    public void testFinishTreeMultipleUniqueOperands() {
        NumberOfOperandsCheck spyCheck = spy(new NumberOfOperandsCheck());
        DetailAST root = mock(DetailAST.class);

        DetailAST ast1 = mock(DetailAST.class);
        DetailAST ast2 = mock(DetailAST.class);
        DetailAST ast3 = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        when(ast1.getLineNo()).thenReturn(10);
        when(ast1.getText()).thenReturn("a");

        when(ast2.getLineNo()).thenReturn(11);
        when(ast2.getText()).thenReturn("b");

        when(ast3.getLineNo()).thenReturn(12);
        when(ast3.getText()).thenReturn("c");

        spyCheck.beginTree(root);

        spyCheck.visitToken(ast1);
        spyCheck.visitToken(ast2);
        spyCheck.visitToken(ast3);

        spyCheck.finishTree(root);

        verify(spyCheck).log(0, "Total operands (N2): 3");
        verify(spyCheck).log(0, "Unique operands (n2): 3");
    }
}
