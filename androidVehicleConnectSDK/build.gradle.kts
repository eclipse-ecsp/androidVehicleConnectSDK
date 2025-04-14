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
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("org.jetbrains.dokka")
    id("org.cyclonedx.bom")
    id("org.jlleitschuh.gradle.ktlint")
    id("signing")
    id("maven-publish")
}

android {
    namespace = "org.eclipse.ecsp"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        group = "org.eclipse.ecsp"
        version = 1.0
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    ktlint {
        android = true
        ignoreFailures = false
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
        }
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

val androidSourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

artifacts {
    add("archives", androidSourceJar)
}

publishing {
    publications {
//            create<MavenPublication>("mavenJava") {

        register<MavenPublication>("release") {
            groupId = "org.eclipse.ecsp"
            artifactId = "vehicleconnectsdk"
            version = "1.0.0"
            afterEvaluate {
                from(components["release"])
            }
            artifact(androidSourceJar)
            pom {
                name.set("Vehicle Connect SDK")
                description.set("Android Library with vehicle related APIs, contains set of Login and Remote operation API")
                url.set("https://github.com/eclipse-ecsp/androidVehicleConnectSDK.git")
                packaging = "aar"
                scm {
                    connection.set("scm:git@github.com:eclipse-ecsp/androidVehicleConnectSDK.git")
                    developerConnection.set("scm:git@github.com:eclipse-ecsp/androidVehicleConnectSDK.git")
                    url.set("https://github.com/eclipse-ecsp/androidVehicleConnectSDK.git")
                }
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("dileephemachandranharman")
                        name.set("Dileep Hemachandran")
                        email.set("dileep.hemachandran@harman.com")
                        organization.set("eclipse-ecsp")
                        organizationUrl.set("https://github.com/eclipse-ecsp")
                    }
                }
            }
        }
    }
}

/*repositories {
    maven {
        name = "ossrh"
        url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
        credentials {
            username = System.getenv("OSSRH_USERNAME")
            password = System.getenv("OSSRH_PASSWORD")
        }
    }
}*/

signing {
    useInMemoryPgpKeys(
        System.getenv("GPG_SUBKEY_ID"),
        System.getenv("GPG_PRIVATE_KEY"),
        System.getenv("GPG_PASSPHRASE")
    )
    publishing.publications.all {
        sign(this)
    }
}


tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(tasks.withType<Sign>())
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.21")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.google.code.gson:gson:2.12.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("net.openid:appauth:0.11.1")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.concurrent:concurrent-futures:1.1.0")
    implementation("androidx.lifecycle:lifecycle-common:2.7.0")

    implementation("com.google.dagger:dagger:2.55")
    implementation("com.google.dagger:dagger-android:2.55")
    implementation("com.google.dagger:dagger-android-support:2.55")
    kapt("com.google.dagger:dagger-android-processor:2.55")
    kapt("com.google.dagger:dagger-compiler:2.55")
}
