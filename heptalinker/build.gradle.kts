plugins {
    alias(libs.plugins.android.library)
    id("maven-publish")
}


android {
    namespace = "com.hepta.linker"
    compileSdk = 35
    buildToolsVersion = "35.0.0"
    ndkVersion = "27.0.12077973"
    defaultConfig {
        minSdk = 28
        externalNativeBuild {
            cmake {
//                abiFilters ("armeabi-v7a", "arm64-v8a")

            }
        }
    }
    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    // Enable generation of Prefab packages and include them in the library's AAR.
    buildFeatures {
        buildConfig = false
        prefabPublishing = true
        androidResources = false
    }


    prefab {
        register("heptalinker") {
            headers = "src/main/cpp/include"
        }
    }

    packaging {
        jniLibs {
            excludes += "**.so"
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}



afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.Thehepta"
                artifactId = "HeptaLinker"
                version = "0.0.4"

                // 指定 AAR 文件路径
//                artifact(tasks.named("bundleReleaseAar"))

                // 可选：添加 pom 文件信息
                pom {
                    name.set("HeptaLinkerNative")
                    description.set("An Android library for linking native code.")
                    url.set("https://github.com/Thehepta/HeptaLinker ")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT ")
                        }
                    }
                    developers {
                        developer {
                            name = "thehepta"
                            url = "https://github.com/thehepta"
                        }
                    }
                    scm {
                        url.set("https://github.com/thehepta/heptalinker ")
                    }
                }
            }
        }
    }
}