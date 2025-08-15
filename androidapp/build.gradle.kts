plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.app"

    compileSdk = 36

    defaultConfig {
        applicationId = "com.seu.pacote"
        minSdk = 21
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    kotlin {
        jvmToolchain(21)
    }
    
}

dependencies {

    implementation(project(":common-serialization"))
    implementation(project(":common"))
    implementation(project(":games"))

    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    // OkHttp para WebSocket e HTTP base
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.compose.ui:ui:1.9.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.9.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.2")
    implementation("androidx.navigation:navigation-compose:2.9.3")

    implementation("com.android.support:appcompat-v7:28.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}
