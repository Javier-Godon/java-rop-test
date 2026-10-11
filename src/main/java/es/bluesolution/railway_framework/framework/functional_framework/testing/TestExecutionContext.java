package es.bluesolution.railway_framework.framework.functional_framework.testing;

import es.bluesolution.railway_framework.framework.functional_framework.Result;
import es.bluesolution.railway_framework.framework.functional_framework.execution.ExecutionContext;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

/**
 * Test execution context for unit testing handlers without transactions.
 *
 * This context immediately executes the computation without any wrapping,
 * making it ideal for unit tests where you want to test handler logic
 * without Spring's transaction infrastructure.
 *
 * Usage:
 *
 * @ExtendWith(MockitoExtension.class)
 * class CreateCategoryHandlerTest {
 *
 *     @Mock private CatalogRepository repository;
 *     private CreateCategoryHandler handler;
 *
 *     @BeforeEach
 *     void setUp() {
 *         // ✅ One-line setup instead of 10-line anonymous class
 *         handler = new CreateCategoryHandler(repository, TestExecutionContext.IMMEDIATE);
 *     }
 * }
 *
 * The IMMEDIATE constant is preferred for most tests as it clearly indicates
 * that the computation executes synchronously without any delays or wrapping.
 *
 * @see es.bluesolution.railway_framework.framework.functional_framework.execution.NoOpExecutionContext
 * @see es.bluesolution.railway_framework.framework.functional_framework.execution.TransactionExecutionContext
 */
@NullMarked
public final class TestExecutionContext implements ExecutionContext {

    /**
     * Singleton instance that immediately executes computations.
     * Use this in test @BeforeEach methods for handler setup.
     */
    public static final TestExecutionContext IMMEDIATE = new TestExecutionContext();

    /**
     * Private constructor - use {@link #IMMEDIATE} singleton.
     */
    private TestExecutionContext() {
        // Singleton pattern
    }

    /**
     * Immediately executes the computation without any wrapping.
     *
     * This is equivalent to:
     *   computation.get()
     *
     * No transactions, no logging, no side effects.
     *
     * @param computation the Result-returning operation to execute
     * @param <T> the success value type
     * @return the Result exactly as returned by the computation
     */
    @Override
    public <T> Result<T> execute(Supplier<Result<T>> computation) {
        return computation.get();
    }
}
