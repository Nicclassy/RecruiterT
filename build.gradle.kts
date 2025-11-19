plugins {
    id("java")
    id("io.freefair.aspectj.post-compile-weaving") version "9.1.0"
    id("io.freefair.lombok") version "9.1.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springframework.boot") version "3.5.7"
    kotlin("jvm") version "2.2.20"
}

apply(plugin = "io.freefair.aspectj.post-compile-weaving")
apply(plugin = "io.freefair.lombok")

group = "org.recruitert"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // AspectJ
    implementation("org.aspectj:aspectjrt:1.9.24")
    implementation("org.aspectj:aspectjweaver:1.9.24")

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter:3.5.6")

    // Hibernate
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    testCompileOnly("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.42")

    // Playwright
    implementation("com.microsoft.playwright:playwright:1.55.0")

    // JUnit
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Other
    testImplementation(kotlin("test"))
}

tasks.register("run", org.springframework.boot.gradle.tasks.run.BootRun::class) {
    mainClass.set("org.recruitert.Main")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.withType<JavaCompile> {
    options.annotationProcessorPath = configurations["annotationProcessor"]
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(22)
}