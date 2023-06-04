plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization.json)
}

android {
    namespace = "io.github.ilyadreamix.httptools"
    compileSdk = 33

    defaultConfig {
        applicationId = "io.github.ilyadreamix.httptools"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.accompanist.flow.layout)

    implementation(libs.android.core.ktx)
    implementation(libs.android.lifecycle.runtime.ktx)

    implementation(libs.balloon.compose)

    implementation(libs.compose.activity)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material.icons)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.viewmodel)

    implementation(libs.kotlin.serialization.json)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.cio)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.core)

    debugImplementation(libs.compose.ui.test.manifest)
    debugImplementation(libs.compose.ui.tooling)
}
