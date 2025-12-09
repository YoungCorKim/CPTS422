package CheckStyleTestPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Helper "engine" to run a single custom Checkstyle check
 * on a given Java source file and collect its log messages.
 */
public final class BlackboxTestEngine {

    private BlackboxTestEngine() {
        // utility class
    }

    /**
     * Runs the given check class on the given Java file and returns
     * all messages produced by log(...).
     */
    public static List<String> runCheckOnFile(
            Class<? extends com.puppycrawl.tools.checkstyle.api.AbstractCheck> checkClass,
            File javaFile) throws Exception {

        // Create the checker
        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());

        // Root configuration
        DefaultConfiguration rootConfig = new DefaultConfiguration("Checker");

        // TreeWalker (this is the part that walks the AST, like in the hint)
        DefaultConfiguration treeWalkerConfig = new DefaultConfiguration("TreeWalker");

        // Configuration for your specific check
        DefaultConfiguration checkConfig =
                new DefaultConfiguration(checkClass.getCanonicalName());

        treeWalkerConfig.addChild(checkConfig);
        rootConfig.addChild(treeWalkerConfig);

        // Our listener to capture events
        CollectingAuditListener listener = new CollectingAuditListener();
        checker.addListener(listener);

        checker.configure(rootConfig);

        // Run the checker on the given file
        checker.process(Collections.singletonList(javaFile));

        checker.destroy();
        return listener.getMessages();
    }

    /**
     * Simple AuditListener that just collects the messages produced by checks.
     */
    private static class CollectingAuditListener extends AutomaticBean implements AuditListener {

        private final List<String> messages = new ArrayList<>();

        @Override
        public void addError(AuditEvent event) {
            // Because your checks call log(0, "full message"),
            // event.getMessage() will return that string.
            messages.add(event.getMessage());
        }

        @Override
        public void addException(AuditEvent event, Throwable throwable) {
            messages.add("EXCEPTION: " + throwable.getMessage());
        }

        // The remaining methods are required by the interface but not needed
        @Override public void auditStarted(AuditEvent event) { }
        @Override public void auditFinished(AuditEvent event) { }
        @Override public void fileStarted(AuditEvent event) { }
        @Override public void fileFinished(AuditEvent event) { }

        public List<String> getMessages() {
            return messages;
        }

		@Override
		protected void finishLocalSetup() throws CheckstyleException {
			// TODO Auto-generated method stub
			
		}
    }
}
