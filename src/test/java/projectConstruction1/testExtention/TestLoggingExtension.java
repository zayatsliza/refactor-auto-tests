package projectConstruction1.testExtention;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLoggingExtension implements TestWatcher {

    private static final Logger log =
            LoggerFactory.getLogger(TestLoggingExtension.class);

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.error("[ERROR] Test FAILED: {}",
                context.getDisplayName(), cause);
    }
}
