import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.10.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "dev.eckler"
version = "0.0.1"

object Versions {
    const val java = 21
    const val lombok = "1.18.36"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-h2console")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-session-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.flywaydb:flyway-database-postgresql")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-session-jdbc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.testcontainers:testcontainers-postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")



    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.0")

    testImplementation("com.github.dasniko:testcontainers-keycloak:4.0.1")
    testImplementation("io.rest-assured:rest-assured:6.0.0")

    compileOnly("org.projectlombok:lombok:${Versions.lombok}")
    annotationProcessor("org.projectlombok:lombok:${Versions.lombok}")
    testCompileOnly("org.projectlombok:lombok:${Versions.lombok}")
    testAnnotationProcessor("org.projectlombok:lombok:${Versions.lombok}")

}

sourceSets {
    main {
        java {
            srcDir(layout.buildDirectory.dir("generate-resources/main/src/main/java"))
        }
    }
}

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName.set("cashflow-api:dev")
}

tasks.register("bootRunDev") {
    group = "application"
    description = "dev mode"
    doFirst {
        tasks.bootRun.configure {
            systemProperty("spring.profiles.active", "dev")
        }
    }
    finalizedBy("bootRun")
}

tasks.named("compileJava") {
    dependsOn(tasks.openApiGenerate)
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("../cashflow-api.yml")
    ignoreFileOverride.set("src/main/resources/.openapi-generator-ignore")
    modelPackage.set("dev.eckler.cashflow.openapi.model")
    apiPackage.set("dev.eckler.cashflow.openapi.api")
    configOptions.set(
        mapOf(
            "useSpringBoot3" to "true",
            "openApiNullable" to "false",
            "interfaceOnly" to "true",
            "useTags" to "true",
        ),
    )
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
        )
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
        // TODO: keep this uncommented to clean up the logs in ci/cd
        showStandardStreams = true
    }
    // testLogging {
    //     lifecycle {
    //         events =
    //             mutableSetOf(
    //                 TestLogEvent.FAILED,
    //                 TestLogEvent.PASSED,
    //                 TestLogEvent
    //                     .SKIPPED,
    //             )
    //         exceptionFormat = TestExceptionFormat.FULL
    //
    //         showExceptions = true
    //         showCauses = true
    //         showStackTraces = false
    //         showStandardStreams = true
    //     }
    //     info.events = lifecycle.events
    //     info.exceptionFormat = lifecycle.exceptionFormat
    // }
    //
    // val failedTests = mutableListOf<TestDescriptor>()
    // val skippedTests = mutableListOf<TestDescriptor>()
    //
    // addTestListener(
    //     object : TestListener {
    //         override fun beforeSuite(suite: TestDescriptor) {}
    //
    //         override fun beforeTest(testDescriptor: TestDescriptor) {}
    //
    //         override fun afterTest(
    //             testDescriptor: TestDescriptor,
    //             result: TestResult,
    //         ) {
    //             when (result.resultType) {
    //                 TestResult.ResultType.FAILURE -> failedTests.add(testDescriptor)
    //                 TestResult.ResultType.SKIPPED -> skippedTests.add(testDescriptor)
    //                 else -> Unit
    //             }
    //         }
    //
    //         override fun afterSuite(
    //             suite: TestDescriptor,
    //             result: TestResult,
    //         ) {
    //             if (suite.parent == null) {
    //                 logger.lifecycle("################ Summary::Start ################")
    //                 logger.lifecycle("Test result: ${result.resultType}")
    //                 logger.lifecycle(
    //                     "Test summary: ${result.testCount} tests, " +
    //                         "${result.successfulTestCount} succeeded, " +
    //                         "${result.failedTestCount} failed, " +
    //                         "${result.skippedTestCount} skipped",
    //                 )
    //                 failedTests.takeIf { it.isNotEmpty() }?.prefixedSummary("\tFailed Tests")
    //                 skippedTests.takeIf { it.isNotEmpty() }?.prefixedSummary("\tSkipped Tests:")
    //                 logger.lifecycle("################ Summary::End ##################")
    //             }
    //         }
    //
    //         private infix fun List<TestDescriptor>.prefixedSummary(subject: String) {
    //             logger.lifecycle(subject)
    //             forEach { test -> logger.lifecycle("\t\t${test.displayName()}") }
    //         }
    //
    //         private fun TestDescriptor.displayName() = parent?.let { "${it.name} - $name" } ?: "$name"
    //     },
    // )
}
