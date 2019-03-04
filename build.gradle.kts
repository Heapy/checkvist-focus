import Versions.checkvistVersion
import Versions.kotlinVersion

plugins {
    kotlin("jvm").version(Versions.kotlinVersion)
    application
}

repositories {
    jcenter()
    maven {
        url = uri("https://dl.bintray.com/heapy/heap-dev/")
    }
}

application {
    mainClassName = "io.heapy.checkvist.focus.App"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("io.heapy.checkvist.jvm:jvm:$checkvistVersion")
}
