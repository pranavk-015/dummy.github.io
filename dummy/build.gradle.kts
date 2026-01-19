plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
    signing
}

android {
    namespace = "io.github.pranavk015.dummylibrary"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        targetSdk = 36
        version = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

// Tasks for sourcesJar and javadocJar
val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}

dependencies {
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.compose.ui:ui:1.9.2")
    // Add other dependencies as needed
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.github.pranavk015"
            artifactId = "dummylibrary"
            version = "1.0.0"

            // Main AAR
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")
            // Source and Javadoc
            artifact(sourcesJar)
            artifact(javadocJar)

            pom {
                name.set("Dummy Library")
                description.set("A demo Android library")
                url.set("https://github.com/pranavk-015/dummypackage")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("pranavk015")
                        name.set("Pranav K")
                        email.set("youremail@example.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/pranavk-015/dummypackage.git")
                    developerConnection.set("scm:git:ssh://github.com/pranavk-015/dummypackage.git")
                    url.set("https://github.com/pranavk-015/dummypackage")
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

        }
    }
}


