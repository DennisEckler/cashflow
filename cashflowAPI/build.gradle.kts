import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("org.openapi.generator") version "7.10.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}
apply(plugin = "io.spring.dependency-management")

group = "dev.eckler"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.0")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")
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
    inputSpec.set("src/main/resources/cashflow-api.yml")
    ignoreFileOverride.set("src/main/resources/.openapi-generator-ignore")
    invokerPackage.set("dev.eckler.cashflow.openapi.invoker")
    modelPackage.set("dev.eckler.cashflow.openapi.model")
    apiPackage.set("dev.eckler.cashflow.openapi.api")
    configOptions.set(
        mapOf(
            "useSpringBoot3" to "true",
            "openApiNullable" to "false",
        ),
    )
}

tasks.withType<Test> {
    useJUnitPlatform()
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
