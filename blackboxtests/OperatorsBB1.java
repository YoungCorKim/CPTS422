package CheckStyleTestPackage;
import java.util.function.Function;

public class OperatorsBB1 {

    public int operators(int a, int b, Object o) {
        int x = a + b - 3 * 2 / 1 % 2;   // +, -, *, /, %

        x += 5;                          // +=
        x--;                             // postfix --
        ++x;                             // prefix ++
        boolean cond = (x > 0) && (a == b) || (a != b); // >, &&, ==, ||, !=

        o = (o == null) ? new String("x") : o; // ==, ?:, new

        if (o instanceof String) {       // instanceof
            x = x << 1;                  // <<
            x = x >> 1;                  // >>
        }

        Function<Integer, Integer> f = y -> y * 2; // lambda arrow ->
        return cond ? f.apply(x) : x;
    }
}
