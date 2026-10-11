package es.bluesolution.railway_framework.framework.functional_framework.testing;

import es.bluesolution.railway_framework.framework.functional_framework.FailureResultDescription;
import es.bluesolution.railway_framework.framework.functional_framework.FailureResultDescription.ErrorCode;
import es.bluesolution.railway_framework.framework.functional_framework.Result;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test assertion utilities for Railway-Oriented Programming Result type.
 *
 * Provides fluent assertions for Result<T> that simplify test code:
 *
 * Before:
 *   assertTrue(result.isSuccess());
 *   assertEquals(expected, result.value().id());
 *
 * After:
 *   assertSuccess(result);
 *   assertEquals(expected, assertSuccessAndGet(result).id());
 *
 * Or with error checking:
 *   assertFailureWithCode(result, VALIDATION_ERROR);
 *   assertFailureContains(result, "field is required");
 *
 * @see Result for the Result monad definition
 */
public final class ResultAssertions {

    private ResultAssertions() {
        // Utility class - prevent instantiation
    }

    // =========================================================================
    // Success Assertions
    // =========================================================================

    /**
     * Assert that the Result is a success.
     *
     * @param result the Result to check
     * @param <T> the success value type
     * @throws AssertionError if the Result is a failure
     */
    public static <T> void assertSuccess(Result<T> result) {
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isSuccess(),
                () -> "Expected Success but got Failure: " + result.failure().message());
    }

    /**
     * Assert that the Result is a success and return the value.
     * Useful for chaining further assertions on the success value.
     *
     * @param result the Result to check
     * @param <T> the success value type
     * @return the success value
     * @throws AssertionError if the Result is a failure
     */
    public static <T> T assertSuccessAndGet(Result<T> result) {
        assertSuccess(result);
        return result.value();
    }

    /**
     * Assert that the Result is a success with a specific value.
     *
     * @param expected the expected success value
     * @param result the Result to check
     * @param <T> the success value type
     * @throws AssertionError if the Result is a failure or value doesn't match
     */
    public static <T> void assertSuccessEquals(T expected, Result<T> result) {
        assertSuccess(result);
        assertEquals(expected, result.value());
    }

    // =========================================================================
    // Failure Assertions
    // =========================================================================

    /**
     * Assert that the Result is a failure.
     *
     * @param result the Result to check
     * @param <T> the success value type
     * @throws AssertionError if the Result is a success
     */
    public static <T> void assertFailure(Result<T> result) {
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isFailure(),
                () -> "Expected Failure but got Success: " + result.value());
    }

    /**
     * Assert that the Result is a failure and return the failure description.
     * Useful for chaining further assertions on the failure.
     *
     * @param result the Result to check
     * @param <T> the success value type
     * @return the failure description
     * @throws AssertionError if the Result is a success
     */
    public static <T> FailureResultDescription assertFailureAndGet(Result<T> result) {
        assertFailure(result);
        return result.failure();
    }

    /**
     * Assert that the Result is a failure with a specific error code.
     *
     * @param result the Result to check
     * @param expectedCode the expected error code
     * @param <T> the success value type
     * @throws AssertionError if the Result is a success or code doesn't match
     */
    public static <T> void assertFailureWithCode(Result<T> result, ErrorCode expectedCode) {
        assertFailure(result);
        assertEquals(expectedCode, result.failure().code(),
                () -> "Expected error code " + expectedCode + " but got " + result.failure().code() +
                        " with message: " + result.failure().message());
    }

    /**
     * Assert that the Result is a failure with a specific error code and message.
     *
     * @param result the Result to check
     * @param expectedCode the expected error code
     * @param expectedMessage the expected error message
     * @param <T> the success value type
     * @throws AssertionError if the Result is a success or code/message don't match
     */
    public static <T> void assertFailureEquals(
            Result<T> result,
            ErrorCode expectedCode,
            String expectedMessage) {
        assertFailure(result);
        assertEquals(expectedCode, result.failure().code());
        assertEquals(expectedMessage, result.failure().message());
    }

    /**
     * Assert that the Result is a failure with a message containing the given substring.
     *
     * @param result the Result to check
     * @param messageSubstring the substring that should be in the error message
     * @param <T> the success value type
     * @throws AssertionError if the Result is a success or message doesn't contain substring
     */
    public static <T> void assertFailureContains(Result<T> result, String messageSubstring) {
        assertFailure(result);
        assertTrue(result.failure().message().contains(messageSubstring),
                () -> "Expected failure message to contain '" + messageSubstring +
                        "' but was: " + result.failure().message());
    }

    /**
     * Assert that the Result is a failure with a specific error code and
     * message containing the given substring.
     *
     * @param result the Result to check
     * @param expectedCode the expected error code
     * @param messageSubstring the substring that should be in the error message
     * @param <T> the success value type
     * @throws AssertionError if the Result is a success, code doesn't match,
     *         or message doesn't contain substring
     */
    public static <T> void assertFailureWithCodeAndContains(
            Result<T> result,
            ErrorCode expectedCode,
            String messageSubstring) {
        assertFailureWithCode(result, expectedCode);
        assertTrue(result.failure().message().contains(messageSubstring),
                () -> "Expected failure message to contain '" + messageSubstring +
                        "' but was: " + result.failure().message());
    }

    /**
     * Assert that the Result is a failure with an exception of a specific type.
     *
     * @param result the Result to check
     * @param expectedExceptionType the expected exception class
     * @param <T> the success value type
     * @throws AssertionError if the Result is a success, has no exception,
     *         or exception type doesn't match
     */
    public static <T> void assertFailureWithException(
            Result<T> result,
            Class<? extends Exception> expectedExceptionType) {
        assertFailure(result);
        assertNotNull(result.failure().exception(),
                "Expected failure to have an exception but it was null");
        assertInstanceOf(expectedExceptionType, result.failure().exception(),
                () -> "Expected exception of type " + expectedExceptionType.getSimpleName() +
                        " but got " + result.failure().exception().getClass().getSimpleName());
    }

    // =========================================================================
    // Convenience Error Code Assertions
    // =========================================================================

    /**
     * Assert that the Result is a VALIDATION_ERROR failure.
     *
     * @param result the Result to check
     * @param <T> the success value type
     */
    public static <T> void assertValidationError(Result<T> result) {
        assertFailureWithCode(result, ErrorCode.VALIDATION_ERROR);
    }

    /**
     * Assert that the Result is a VALIDATION_ERROR failure with message containing substring.
     *
     * @param result the Result to check
     * @param messageSubstring the substring that should be in the error message
     * @param <T> the success value type
     */
    public static <T> void assertValidationError(Result<T> result, String messageSubstring) {
        assertFailureWithCodeAndContains(result, ErrorCode.VALIDATION_ERROR, messageSubstring);
    }

    /**
     * Assert that the Result is a NOT_FOUND failure.
     *
     * @param result the Result to check
     * @param <T> the success value type
     */
    public static <T> void assertNotFoundError(Result<T> result) {
        assertFailureWithCode(result, ErrorCode.NOT_FOUND);
    }

    /**
     * Assert that the Result is a BUSINESS_RULE_ERROR failure.
     *
     * @param result the Result to check
     * @param <T> the success value type
     */
    public static <T> void assertBusinessRuleError(Result<T> result) {
        assertFailureWithCode(result, ErrorCode.BUSINESS_RULE_ERROR);
    }

    /**
     * Assert that the Result is a DATABASE_ERROR failure.
     *
     * @param result the Result to check
     * @param <T> the success value type
     */
    public static <T> void assertDatabaseError(Result<T> result) {
        assertFailureWithCode(result, ErrorCode.DATABASE_ERROR);
    }

    /**
     * Assert that the Result is an AUTHENTICATION_ERROR failure.
     *
     * @param result the Result to check
     * @param <T> the success value type
     */
    public static <T> void assertAuthenticationError(Result<T> result) {
        assertFailureWithCode(result, ErrorCode.AUTHENTICATION_ERROR);
    }

    /**
     * Assert that the Result is an AUTHORIZATION_ERROR failure.
     *
     * @param result the Result to check
     * @param <T> the success value type
     */
    public static <T> void assertAuthorizationError(Result<T> result) {
        assertFailureWithCode(result, ErrorCode.AUTHORIZATION_ERROR);
    }

    /**
     * Assert that the Result is an EXTERNAL_SERVICE_ERROR failure.
     *
     * @param result the Result to check
     * @param <T> the success value type
     */
    public static <T> void assertExternalServiceError(Result<T> result) {
        assertFailureWithCode(result, ErrorCode.EXTERNAL_SERVICE_ERROR);
    }
}
