plugins {
    id("org.jetbrains.dokka") version ("1.9.10")
    id("org.cyclonedx.bom") version ("1.9.0")
    id("org.jlleitschuh.gradle.ktlint") version ("12.2.0")
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "org.cyclonedx.bom")
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
        classpath("org.cyclonedx:cyclonedx-gradle-plugin:1.9.0")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:12.2.0")
    }
}

group = "org.eclipse.ecsp"
version = 1.0
project.allprojects {
    tasks.cyclonedxBom {
        outputs.cacheIf { true }
        setIncludeConfigs(listOf("debugCompileClasspath"))
        setSkipConfigs(
            listOf(
                "debugAndroidTestCompileClasspath",
                "debugUnitTestCompileClasspath",
                "releaseUnitTestCompileClasspath",
                "debugUnitTestRuntimeClasspath",
                "releaseUnitTestRuntimeClasspath",
                "androidVehicleConnectSDK:debugApiElements",
                "app:debugApiElements",
                ":androidVehicleConnectSDK:debugRuntimeElements",
                ":androidVehicleConnectSDK:releaseRuntimeElements",
            ),
        )
        setProjectType("library")
        outputFormat = "xml"
    }
}
