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

## Installation (GitHub Packages)

Published from this repository to GitHub Packages on every push to `main` (`-SNAPSHOT`) and on
tags `v*` (releases). GitHub's Maven registry requires a token even for public packages: use a
classic PAT with `read:packages` locally, or `GITHUB_TOKEN` in GitHub Actions.

```xml
<!-- pom.xml -->
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/Javier-Godon/java-rop-test</url>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>

<!-- ~/.m2/settings.xml -->
<server>
    <id>github</id>
    <username>${env.GITHUB_ACTOR}</username>
    <password>${env.GITHUB_TOKEN}</password>
</server>
```

A copy is also published to the private Nexus (`./mvnw deploy -Pnexus`, see the
[erp-backend implementation plan, Phase 6](https://github.com/Javier-Godon/erp-backend/blob/main/docs/guides/FRAMEWORK_SPLIT_AND_FIRST_IMAGE_IMPLEMENTATION_PLAN.md)).

## Build

```bash
./mvnw verify      # tests, enforcer, sources and javadoc jars
```
