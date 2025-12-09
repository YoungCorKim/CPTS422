package CheckStyleTestPackage;
public class HalsteadLengthBB {

    private Helper helper = new Helper();

    public void compute(int[] arr) {
        // Paired symbols: (), {}, []
        if (arr[0] > 1) {
            helper.process(arr[1], true, null);
        }
    }

    private static class Helper {
        void process(int value, boolean flag, Object o) {
            if (!flag) {
                value = value - 1;
            }
        }
    }
}
