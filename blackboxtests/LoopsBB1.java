package CheckStyleTestPackage;
import java.util.List;

public class LoopsBB1 {

    public void loops(List<String> list) {
        // classic for
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
        }

        // enhanced for
        for (String s : list) {
            if (s.isEmpty()) {
                continue;
            }
        }

        // while loop
        int i = 0;
        while (i < 3) {
            i++;
        }

        // do-while loop
        int j = 0;
        do {
            j++;
        } while (j < 2);

        // should NOT be counted as loops:
        // while (true) { }  // loop in comment
        String fake = "for (int k = 0; k < 10; k++)"; // loop in string

        list.forEach(e -> System.out.println(e)); // library call, not language loop
    }
}
