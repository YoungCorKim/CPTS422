package CheckStyleTestPackage;
public class OperandsBB1 {

    private static final int CONST = 42;
    private int[] data = new int[3];

    public int compute(int param) {
        int local = param + CONST;   // literals + static field + parameter + local

        data[0] = local;
        data[1] = data[0] + 5;

        Holder h = new Holder();
        return h.value + data[1];    // field access + array access
    }

    private static class Holder {
        int value = 10;
    }
}
