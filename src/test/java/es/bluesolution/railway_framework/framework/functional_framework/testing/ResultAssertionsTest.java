package es.bluesolution.railway_framework.framework.functional_framework.testing;

import es.bluesolution.railway_framework.framework.functional_framework.FailureResultDescription;
import es.bluesolution.railway_framework.framework.functional_framework.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static es.bluesolution.railway_framework.framework.functional_framework.FailureResultDescription.ErrorCode.*;
import static es.bluesolution.railway_framework.framework.functional_framework.testing.ResultAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ResultAssertions utility class.
 */
@DisplayName("ResultAssertions - Test Utilities")
class ResultAssertionsTest {

    // =========================================================================
    // Success Assertions
    // =========================================================================

    @Nested
    @DisplayName("assertSuccess()")
    class AssertSuccessTests {

        @Test
        @DisplayName("Should pass for success Result")
        void shouldPassForSuccessResult() {
            // Arrange
            Result<String> result = Result.success("value");

            // Act & Assert - should not throw
            assertDoesNotThrow(() -> assertSuccess(result));
        }

        @Test
        @DisplayName("Should fail for failure Result")
        void shouldFailForFailureResult() {
            // Arrange
            Result<String> result = Result.failure(VALIDATION_ERROR, "error", null);

            // Act & Assert
            AssertionFailedError error = assertThrows(AssertionFailedError.class,
                    () -> assertSuccess(result));
            assertTrue(error.getMessage().contains("Expected Success but got Failure"));
        }
    }

    @Nested
    @DisplayName("assertSuccessAndGet()")
    class AssertSuccessAndGetTests {

        @Test
        @DisplayName("Should return value for success Result")
        void shouldReturnValueForSuccessResult() {
            // Arrange
            Result<String> result = Result.success("test value");

            // Act
            String value = assertSuccessAndGet(result);

            // Assert
            assertEquals("test value", value);
        }

        @Test
        @DisplayName("Should fail for failure Result")
        void shouldFailForFailureResult() {
            // Arrange
            Result<String> result = Result.failure(NOT_FOUND, "not found", null);

            // Act & Assert
            assertThrows(AssertionFailedError.class, () -> assertSuccessAndGet(result));
        }
    }

    @Nested
    @DisplayName("assertSuccessEquals()")
    class AssertSuccessEqualsTests {

        @Test
        @DisplayName("Should pass when values match")
        void shouldPassWhenValuesMatch() {
            // Arrange
            Result<Integer> result = Result.success(42);

            // Act & Assert
            assertDoesNotThrow(() -> assertSuccessEquals(42, result));
        }

        @Test
        @DisplayName("Should fail when values don't match")
        void shouldFailWhenValuesDontMatch() {
            // Arrange
            Result<Integer> result = Result.success(42);

            // Act & Assert
            assertThrows(AssertionFailedError.class,
                    () -> assertSuccessEquals(100, result));
        }
    }

    // =========================================================================
    // Failure Assertions
    // =========================================================================

    @Nested
    @DisplayName("assertFailure()")
    class AssertFailureTests {

        @Test
        @DisplayName("Should pass for failure Result")
        void shouldPassForFailureResult() {
            // Arrange
            Result<String> result = Result.failure(VALIDATION_ERROR, "error", null);

            // Act & Assert
            assertDoesNotThrow(() -> assertFailure(result));
        }

        @Test
        @DisplayName("Should fail for success Result")
        void shouldFailForSuccessResult() {
            // Arrange
            Result<String> result = Result.success("value");

            // Act & Assert
            AssertionFailedError error = assertThrows(AssertionFailedError.class,
                    () -> assertFailure(result));
            assertTrue(error.getMessage().contains("Expected Failure but got Success"));
        }
    }

    @Nested
    @DisplayName("assertFailureAndGet()")
    class AssertFailureAndGetTests {

        @Test
        @DisplayName("Should return failure description")
        void shouldReturnFailureDescription() {
            // Arrange
            Result<String> result = Result.failure(VALIDATION_ERROR, "test error", null);

            // Act
            FailureResultDescription failure = assertFailureAndGet(result);

            // Assert
            assertEquals(VALIDATION_ERROR, failure.code());
            assertEquals("test error", failure.message());
        }
    }

    @Nested
    @DisplayName("assertFailureWithCode()")
    class AssertFailureWithCodeTests {

        @Test
        @DisplayName("Should pass when error code matches")
        void shouldPassWhenErrorCodeMatches() {
            // Arrange
            Result<String> result = Result.failure(NOT_FOUND, "not found", null);

            // Act & Assert
            assertDoesNotThrow(() -> assertFailureWithCode(result, NOT_FOUND));
        }

        @Test
        @DisplayName("Should fail when error code doesn't match")
        void shouldFailWhenErrorCodeDoesntMatch() {
            // Arrange
            Result<String> result = Result.failure(NOT_FOUND, "not found", null);

            // Act & Assert
            assertThrows(AssertionFailedError.class,
                    () -> assertFailureWithCode(result, VALIDATION_ERROR));
        }
    }

    @Nested
    @DisplayName("assertFailureContains()")
    class AssertFailureContainsTests {

        @Test
        @DisplayName("Should pass when message contains substring")
        void shouldPassWhenMessageContainsSubstring() {
            // Arrange
            Result<String> result = Result.failure(VALIDATION_ERROR, "Name is required", null);

            // Act & Assert
            assertDoesNotThrow(() -> assertFailureContains(result, "required"));
        }

        @Test
        @DisplayName("Should fail when message doesn't contain substring")
        void shouldFailWhenMessageDoesntContainSubstring() {
            // Arrange
            Result<String> result = Result.failure(VALIDATION_ERROR, "Name is required", null);

            // Act & Assert
            assertThrows(AssertionFailedError.class,
                    () -> assertFailureContains(result, "invalid"));
        }
    }

    @Nested
    @DisplayName("assertFailureWithException()")
    class AssertFailureWithExceptionTests {

        @Test
        @DisplayName("Should pass when exception type matches")
        void shouldPassWhenExceptionTypeMatches() {
            // Arrange
            Result<String> result = Result.failure(
                    DATABASE_ERROR, "DB error", new RuntimeException("connection failed"));

            // Act & Assert
            assertDoesNotThrow(() -> assertFailureWithException(result, RuntimeException.class));
        }

        @Test
        @DisplayName("Should fail when no exception")
        void shouldFailWhenNoException() {
            // Arrange
            Result<String> result = Result.failure(DATABASE_ERROR, "DB error", null);

            // Act & Assert
            assertThrows(AssertionFailedError.class,
                    () -> assertFailureWithException(result, RuntimeException.class));
        }
    }

    // =========================================================================
    // Convenience Error Code Assertions
    // =========================================================================

    @Nested
    @DisplayName("Convenience Error Code Assertions")
    class ConvenienceAssertionTests {

        @Test
        @DisplayName("assertValidationError should pass for VALIDATION_ERROR")
        void assertValidationErrorShouldPass() {
            Result<String> result = Result.failure(VALIDATION_ERROR, "invalid", null);
            assertDoesNotThrow(() -> assertValidationError(result));
        }

        @Test
        @DisplayName("assertValidationError with message should check substring")
        void assertValidationErrorWithMessageShouldCheckSubstring() {
            Result<String> result = Result.failure(VALIDATION_ERROR, "Name is required", null);
            assertDoesNotThrow(() -> assertValidationError(result, "required"));
        }

        @Test
        @DisplayName("assertNotFoundError should pass for NOT_FOUND")
        void assertNotFoundErrorShouldPass() {
            Result<String> result = Result.failure(NOT_FOUND, "not found", null);
            assertDoesNotThrow(() -> assertNotFoundError(result));
        }

        @Test
        @DisplayName("assertBusinessRuleError should pass for BUSINESS_RULE_ERROR")
        void assertBusinessRuleErrorShouldPass() {
            Result<String> result = Result.failure(BUSINESS_RULE_ERROR, "rule violated", null);
            assertDoesNotThrow(() -> assertBusinessRuleError(result));
        }

        @Test
        @DisplayName("assertDatabaseError should pass for DATABASE_ERROR")
        void assertDatabaseErrorShouldPass() {
            Result<String> result = Result.failure(DATABASE_ERROR, "db error", null);
            assertDoesNotThrow(() -> assertDatabaseError(result));
        }

        @Test
        @DisplayName("assertAuthenticationError should pass for AUTHENTICATION_ERROR")
        void assertAuthenticationErrorShouldPass() {
            Result<String> result = Result.failure(AUTHENTICATION_ERROR, "auth error", null);
            assertDoesNotThrow(() -> assertAuthenticationError(result));
        }

        @Test
        @DisplayName("assertAuthorizationError should pass for AUTHORIZATION_ERROR")
        void assertAuthorizationErrorShouldPass() {
            Result<String> result = Result.failure(AUTHORIZATION_ERROR, "authz error", null);
            assertDoesNotThrow(() -> assertAuthorizationError(result));
        }

        @Test
        @DisplayName("assertExternalServiceError should pass for EXTERNAL_SERVICE_ERROR")
        void assertExternalServiceErrorShouldPass() {
            Result<String> result = Result.failure(EXTERNAL_SERVICE_ERROR, "ext error", null);
            assertDoesNotThrow(() -> assertExternalServiceError(result));
        }
    }
}
