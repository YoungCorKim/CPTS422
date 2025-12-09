package CheckStyleTestPackage;
public class CommentLinesBB1 {

    /* Multi-line block comment:
       line 1
       
       line 3 (blank line above should or should not count depending on definition)
    */
    public void method() {
        int x = 0; // inline comment on same line

        String s = "not // a comment"; // real comment as well

        // comment-only line at end of method
    } // end of method
} // end of class // trailing comment at EOF with no extra newline
