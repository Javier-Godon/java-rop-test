# rop-test

JUnit 5 helpers for code that returns `Result<T>`. Use with `test` scope.

```xml
<dependency>
    <groupId>es.bluesolution</groupId>
    <artifactId>rop-test</artifactId>
    <version>${rop.version}</version>
    <scope>test</scope>
</dependency>
```

| Type | Purpose |
|---|---|
| `es.bluesolution.rop.test.ResultAssertions` | `assertSuccess`, `assertSuccessAndGet`, `assertFailureWithCode`, `assertFailureContains`, `assertValidationError`, `assertNotFoundError`, … |
| `es.bluesolution.rop.test.TestExecutionContext` | Runs pipelines synchronously without a transaction manager (`TestExecutionContext.IMMEDIATE`) |

```java
import static es.bluesolution.rop.test.ResultAssertions.*;

@Test
void rejectsEmptyName() {
    Result<CategoryId> result = handler.handle(new CreateCategoryCommand(""));
    assertFailureWithCode(result, FailureResultDescription.ErrorCode.VALIDATION_ERROR);
}
```
