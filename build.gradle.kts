plugins {
    kotlin("jvm") version "2.4.21"
}

group = "com.barneyb.aoc-2026"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}