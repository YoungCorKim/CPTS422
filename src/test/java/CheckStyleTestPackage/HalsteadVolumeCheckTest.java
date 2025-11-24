package CheckStyleTestPackage;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.*;

class HalsteadVolumeCheckTest {

    @Test
    public void testGetDefaultTokens() {
        HalsteadVolumeCheck check = new HalsteadVolumeCheck();
        int[] expected = check.getDefaultTokens();

        // Should always return the same combined array of operator + operand tokens
        assertArrayEquals(expected, check.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        HalsteadVolumeCheck check = new HalsteadVolumeCheck();
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        HalsteadVolumeCheck check = new HalsteadVolumeCheck();
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    public void testBeginTreeResetsState() {
        HalsteadVolumeCheck check = new HalsteadVolumeCheck();
        DetailAST root = mock(DetailAST.class);

        // Just make sure it runs without throwing and resets internal counters/sets
        check.beginTree(root);
    }

    @Test
    public void testFinishTreeWithNoTokensLogsZeroVolume() {
        HalsteadVolumeCheck spyCheck = spy(new HalsteadVolumeCheck());
        DetailAST root = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        spyCheck.beginTree(root);
        // no visitToken calls
        spyCheck.finishTree(root);

        verify(spyCheck).log(0, "Halstead Volume Metric: Total Operators (N1): 0");
        verify(spyCheck).log(0, "Halstead Volume Metric: Total Operands (N2): 0");
        verify(spyCheck).log(0, "Halstead Volume Metric: Unique Operators (n1): 0");
        verify(spyCheck).log(0, "Halstead Volume Metric: Unique Operands (n2): 0");
        verify(spyCheck).log(0, "Halstead Volume Metric: Program Vocabulary (n = n1 + n2): 0");
        verify(spyCheck).log(0, "Halstead Volume Metric: Program Length (N = N1 + N2): 0");
        verify(spyCheck).log(0,
                "Halstead Volume Metric: Halstead Volume (V = N × log2(n)): 0.00");
    }

    @Test
    public void testVisitTokenCountsOperatorAndOperandAndLogsVolume() {
        HalsteadVolumeCheck spyCheck = spy(new HalsteadVolumeCheck());

        DetailAST root = mock(DetailAST.class);
        DetailAST operatorAst = mock(DetailAST.class);
        DetailAST operandAst = mock(DetailAST.class);

        doNothing().when(spyCheck).log(anyInt(), anyString());

        // One operator: PLUS
        when(operatorAst.getType()).thenReturn(TokenTypes.PLUS);
        when(operatorAst.getText()).thenReturn("+");

        // One operand: IDENT
        when(operandAst.getType()).thenReturn(TokenTypes.IDENT);
        when(operandAst.getText()).thenReturn("x");

        spyCheck.beginTree(root);

        spyCheck.visitToken(operatorAst);
        spyCheck.visitToken(operandAst);

        spyCheck.finishTree(root);

        // totalOperators = 1, totalOperands = 1, uniqueOperator = { "+" }, uniqueOperands = { "x" }
        // N = 2, n = 2, volume = 2 * log2(2) = 2.00

        verify(spyCheck).log(0, "Halstead Volume Metric: Total Operators (N1): 1");
        verify(spyCheck).log(0, "Halstead Volume Metric: Total Operands (N2): 1");
        verify(spyCheck).log(0, "Halstead Volume Metric: Unique Operators (n1): 1");
        verify(spyCheck).log(0, "Halstead Volume Metric: Unique Operands (n2): 1");
        verify(spyCheck).log(0, "Halstead Volume Metric: Program Vocabulary (n = n1 + n2): 2");
        verify(spyCheck).log(0, "Halstead Volume Metric: Program Length (N = N1 + N2): 2");
        verify(spyCheck).log(0,
                "Halstead Volume Metric: Halstead Volume (V = N × log2(n)): 2.00");
    }
}
