package CheckStyleTestPackage;
public class HalsteadVolumeBB1 {

    // Very small: should produce a small but valid Volume (edge case for n)
    public int alwaysFive() {
        return 5;
    }

    // More complex: bigger N and n to stress the formula
    public int complex(int a, int b, int c) {
        int x = a + b * c - 3;
        if (x > 10 && a != b) {
            x = x / 2 + a;
        } else {
            x = x * 2 - b;
        }
        return x;
    }
}
