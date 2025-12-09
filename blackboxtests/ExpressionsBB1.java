package CheckStyleTestPackage;
public class ExpressionsBB1 {

    public int compute(int a, int b, int c) {
        int x = a + b * c;           // assignment + complex expression
        if ((x > 10) && (a != b)) {  // condition with multiple sub-expressions
            x = adjust(x, a + b);    // argument expressions
        }
        return x - 1;                // return expression
    }

    private int adjust(int value, int offset) {
        return value + offset * 2;
    }
}
