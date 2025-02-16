plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.nightwolf.rsmobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nightwolf.rsmobile"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        val localProperties = org.jetbrains.kotlin.konan.properties.Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")

        if (!localPropertiesFile.exists()) {
            throw GradleException("""
                local.properties file not found.
                Please create it and add MAPTILER_API_KEY=your_api_key_here
            """.trimIndent())
        }

        localProperties.load(localPropertiesFile.inputStream())
        val apiKey = localProperties["MAPTILER_API_KEY"] as? String
            ?: throw GradleException("""
                MAPTILER_API_KEY not found in local.properties.
                Please add: MAPTILER_API_KEY=your_api_key_here
            """.trimIndent())

        buildConfigField("String", "MAPTILER_API_KEY", "\"$apiKey\"")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.squareup.converter.gson)
    implementation(libs.hilt.android)
    implementation(libs.jetbrains.kotlinx.coroutines.play.services)
    implementation(libs.play.services.location)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.coil.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}
