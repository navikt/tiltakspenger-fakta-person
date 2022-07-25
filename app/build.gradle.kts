val javaVersion = JavaVersion.VERSION_17
val ktorVersion = "2.0.3"
val kotlinxSerializationVersion = "1.3.3"
val graphqlKotlinVersion = "5.3.1"
val kotestVersion = "5.4.0"

plugins {
    kotlin("plugin.serialization") version "1.6.21"
    application
}

dependencies {
    implementation(project(":azureAuth"))
    // detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(kotlin("stdlib"))
    implementation("com.github.navikt:rapids-and-rivers:2022061809451655538329.d6deccc62862")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
    implementation("org.jetbrains:annotations:23.0.0")
    // Ktor client
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    testImplementation("io.ktor:ktor-client-mock:$ktorVersion")
    testImplementation("io.ktor:ktor-client-mock-jvm:$ktorVersion")
    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:$kotlinxSerializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:$kotlinxSerializationVersion")
    // Arrow
    implementation("io.arrow-kt:arrow-core:1.1.2")

    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-json:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.2.5")
    testImplementation("io.kotest:kotest-extensions:$kotestVersion")
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("io.mockk:mockk-dsl-jvm:1.12.4")
    testImplementation("org.skyscreamer:jsonassert:1.5.0")
}

application {
    mainClass.set("no.nav.tiltakspenger.fakta.person.ApplicationKt")
}
