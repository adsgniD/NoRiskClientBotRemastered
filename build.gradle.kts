plugins {
    java
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.21"
    application
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

project.setProperty("mainClassName", "de.polylymer.Manager")

val JVM_VERSION = JavaVersion.VERSION_11
val JVM_VERSION_STRING = JVM_VERSION.versionString

group = "de.polylymer"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
    jcenter()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("dev.kord:kord-core:0.7.0-SNAPSHOT")
    implementation("com.gitlab.kordlib:kordx.emoji:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("io.github.config4k:config4k:0.4.2")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("org.apache.commons:commons-lang3:3.11")
}

application {
    mainClass.set("de.polylymer.ManagerKht")
}

val JavaVersion.versionString
    get() = majorVersion.let {
        val version = it.toInt()
        if (version <= 10) "1.$it" else it
    }

fun org.jetbrains.kotlin.gradle.tasks.KotlinCompile.configureJvmVersion() {
    kotlinOptions.jvmTarget = JVM_VERSION_STRING
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    configureJvmVersion()
    configureJvmVersion()
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

