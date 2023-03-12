import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "11.0.0"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    group = "io.github.gunkim"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
}

subprojects {
    dependencies {
        testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.3")
        testImplementation("io.kotest:kotest-assertions-core-jvm:4.6.3")
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
    tasks.test {
        useJUnitPlatform()
    }
}
