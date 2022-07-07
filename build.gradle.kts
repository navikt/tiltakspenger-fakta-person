val javaVersion = JavaVersion.VERSION_17
val ktorVersion = "2.0.2"
val kotlinxSerializationVersion = "1.3.3"

plugins {
    kotlin("jvm") version "1.7.10"
    id("org.jmailen.kotlinter") version "3.10.0"
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://packages.confluent.io/maven/")
        maven("https://jitpack.io")
    }
    apply(plugin = "org.jmailen.kotlinter")
}

configurations.all {
    // exclude JUnit 4
    exclude(group = "junit", module = "junit")
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

tasks.check {
    // Må ligge på root nivå
    dependsOn("installKotlinterPrePushHook")
}

//detekt {
//    buildUponDefaultConfig = true
//    allRules = false
//    config = files("$projectDir/config/detekt.yml")
//}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    tasks {
        compileKotlin {
            kotlinOptions.jvmTarget = javaVersion.toString()
        }
        compileTestKotlin {
            kotlinOptions.jvmTarget = javaVersion.toString()
            kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        }
        test {
            // JUnit 5 support
            useJUnitPlatform()
        }
    }
    configurations.all {
        // exclude JUnit 4
        exclude(group = "junit", module = "junit")
    }
}
