import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    kotlin("jvm").version("1.3.10")
    application
}

repositories {
    jcenter()
    maven {
        url = uri("https://dl.bintray.com/heapy/heap-dev/")
    }
}

val checkvistVersion: String by project

application {
    mainClassName = "io.heapy.checkvist.focus.App"
}

dependencies {
    implementation(kotlin("stdlib-jdk8", getKotlinPluginVersion()))
    implementation("io.heapy.checkvist.jvm:jvm:$checkvistVersion")
}
