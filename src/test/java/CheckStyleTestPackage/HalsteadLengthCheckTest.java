package CheckStyleTestPackage;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.*;

class HalsteadLengthCheckTest {

    @Test
    public void testGetDefaultTokens() {
        HalsteadLengthCheck check = new HalsteadLengthCheck();
        int[] expected = check.getDefaultTokens();

        // Must return the combination of operator + operand tokens
        assertArrayEquals(expected, check.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        HalsteadLengthCheck check = new HalsteadLengthCheck();
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        HalsteadLengthCheck check = new HalsteadLengthCheck();
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    public void testBeginTreeResetsCounters() {
        HalsteadLengthCheck check = new HalsteadLengthCheck();
        DetailAST root = mock(DetailAST.class);

        check.beginTree(root); // should not throw
    }

    @Test
    public void testVisitTokenCountsOperator() {
        HalsteadLengthCheck spyCheck = spy(new HalsteadLengthCheck());
        DetailAST ast = mock(DetailAST.class);

        when(ast.getType()).thenReturn(TokenTypes.PLUS);
        when(ast.getLineNo()).thenReturn(5);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        spyCheck.beginTree(ast);
        spyCheck.visitToken(ast);
        spyCheck.finishTree(ast);

        // Operator count (N1)
        verify(spyCheck).log(0, "Halstead Length Metric: Total Operators (N1): 1");
        // Operand count (N2)
        verify(spyCheck).log(0, "Halstead Length Metric: Total Operands (N2): 0");
        // Program length = N1 + N2
        verify(spyCheck).log(0, "Halstead Length Metric: Program Length (N = N1 + N2): 1");
    }

    @Test
    public void testVisitTokenCountsOperand() {
        HalsteadLengthCheck spyCheck = spy(new HalsteadLengthCheck());
        DetailAST ast = mock(DetailAST.class);

        when(ast.getType()).thenReturn(TokenTypes.IDENT);
        when(ast.getLineNo()).thenReturn(7);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        spyCheck.beginTree(ast);
        spyCheck.visitToken(ast);
        spyCheck.finishTree(ast);

        verify(spyCheck).log(0, "Halstead Length Metric: Total Operators (N1): 0");
        verify(spyCheck).log(0, "Halstead Length Metric: Total Operands (N2): 1");
        verify(spyCheck).log(0, "Halstead Length Metric: Program Length (N = N1 + N2): 1");
    }

    @Test
    public void testVisitTokenCountsMultipleMixed() {
        HalsteadLengthCheck spyCheck = spy(new HalsteadLengthCheck());

        DetailAST op1 = mock(DetailAST.class);
        DetailAST op2 = mock(DetailAST.class);
        DetailAST id = mock(DetailAST.class);
        DetailAST num = mock(DetailAST.class);
        DetailAST root = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        // Two operators
        when(op1.getType()).thenReturn(TokenTypes.PLUS);
        when(op2.getType()).thenReturn(TokenTypes.MINUS);

        // Two operands
        when(id.getType()).thenReturn(TokenTypes.IDENT);
        when(num.getType()).thenReturn(TokenTypes.NUM_INT);

        spyCheck.beginTree(root);

        spyCheck.visitToken(op1);
        spyCheck.visitToken(op2);
        spyCheck.visitToken(id);
        spyCheck.visitToken(num);

        spyCheck.finishTree(root);

        verify(spyCheck).log(0, "Halstead Length Metric: Total Operators (N1): 2");
        verify(spyCheck).log(0, "Halstead Length Metric: Total Operands (N2): 2");
        verify(spyCheck).log(0, "Halstead Length Metric: Program Length (N = N1 + N2): 4");
    }
}
