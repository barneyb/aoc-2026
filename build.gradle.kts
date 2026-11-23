plugins {
    kotlin("jvm") version "2.4.21"
    application
}

group = "com.barneyb.aoc-2026"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // The kotlin compiler uses annotations from a newer version than it bundles
    compileOnly("org.jetbrains:annotations:26.0.2")

    implementation("com.github.ajalt.clikt:clikt-core:5.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.5.2")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-jdk14:2.0.20")

    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.0")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("com.barneyb.aoc2026.MainKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.test {
    useJUnitPlatform()
}
