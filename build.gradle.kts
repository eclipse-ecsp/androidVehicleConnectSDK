/********************************************************************************
 * Copyright (c) 2023-24 Harman International
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ********************************************************************************/
plugins {
    id("org.jetbrains.dokka") version ("1.9.10")
    id("org.cyclonedx.bom") version ("1.9.0")
    id("org.jlleitschuh.gradle.ktlint") version ("12.2.0")
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
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
version = "1.0.0"
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



nexusPublishing {
    repositories {
        sonatype {
            username = System.getenv("OSSRH_USERNAME")
            password = System.getenv("OSSRH_PASSWORD")
            nexusUrl.set(uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/"))
            stagingProfileId = "releases"
        }
    }
}

