package CheckStyleTestPackage;
public class CommentsBB1 {

    // first line comment
    // second line comment

    /* Multi-line
       block comment
       over three lines
    */

    /* first block */ /* second block on same line */

    public void method() {
        int x = 0; // first // second comment on same line

        String s = "/* not a comment */"; // real comment here
    }

    /** Javadoc comment
     *  on two lines
     */
    public void documented() {
        // last comment
    }
}
