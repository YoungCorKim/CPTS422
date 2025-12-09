This project includes a suite of black-box tests used to verify the behavior of each custom Checkstyle metric and to detect faults introduced by mutation testing (PIT).
Black-box tests do not interact with internal data structures; instead, they feed real Java source files into a small test harness (BlackBoxTestEngine) and assert on the final metric values logged by each check.
If a mutation changes the behavior of a metric, the logged output changes and the test fails, killing the mutant.


The engine works by selecting a Java input file from blackbox-tests/ (e.g., HalsteadLengthBB1.java) is passed into

BlackBoxTestEngine.runCheckOnFile(MyCheck.class, inputFile);


The engine:

Creates a Checkstyle Checker

Runs the selected metric on the file

Captures all log(...) messages

The JUnit test compares the actual messages to the expected values.

Any Pit mutations will also fail 


Description of each blackbox test for check
1. HalsteadLengthCheck

Input includes: paired symbols, array access, method calls, literals.
Black-box test catches mutants that:

Miscount () {} []

Fail to count method call tokens

Miscount array indexing (arr[i])

Skip literals (true, null)
Changing any of these affects N1/N2/N and kills the mutant.

2. HalsteadVolumeCheck

Input includes a small method and a complex method.
Mutants detected:

Wrong formula (log10, ln, using n instead of log2(n))

Using wrong N or n

Integer vs floating-point truncation

Edge cases (n ≤ 1)
A volume calculation change breaks the expected output.

3. NumberOfCommentLinesCheck

Input includes inline comments, multi-line block comments, Javadoc, comment-like strings, and EOF comments.
Mutants detected:

Ignoring inline comments

Counting only first/last lines of /* */

Ignoring Javadoc

Counting "not // a comment" as a comment
Any miscount changes the total number of comment lines.

4. NumberOfCommentsCheck

Input includes multi-line blocks, multiple comments on one line, Javadoc, and comment markers inside strings.
Mutants detected:

Treating one /* ... */ block as many comments

Merging separate // comments into one

Ignoring Javadoc

Counting /* */ inside string literals
Wrong comment count → test fails → mutant killed.

5. NumberOfExpressionsCheck

Input includes complex expressions, if-conditions, method-argument expressions, and return expressions.
Mutants detected:

Counting only expression statements

Under-counting complex expressions (e.g., a + b * c)

Over-counting each operator as a separate expression
Mismatch in expression totals kills the mutant.

6. NumberOfLoopsCheck

Input includes for-loops, enhanced for, while, do-while, loop-like comments, loop-like strings, and forEach.
Mutants detected:

Ignoring enhanced for-loops

Missing do-while

Counting loops inside comments or strings

Treating list.forEach() as a loop
Loop count mismatch kills mutants.

7. NumberOfOperandsCheck

Input includes literals, static fields, array accesses, parameters, locals, and field access.
Mutants detected:

Not counting literals (5, true, etc.)

Miscounting qualified names (CONST)

Errors in array or field access (data[i], h.value)

Skipping operands inside expressions
Wrong operand totals kill the mutant.

8. NumberOfOperatorsCheck

Input includes arithmetic, logical, unary, ternary, instanceof, new, shift operators, and lambda arrows.
Mutants detected:

Missing operators (&&, ||, ?:, instanceof, ->)

Confusing = and ==

Miscounting <<, >>, +=, --, ++

Ignoring operators in expressions
Any operator-count change breaks the output



NumberOfOperands and Operators Branch coverage with volume buggy. Will fix with time if thre is.