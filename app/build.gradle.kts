plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.mindspark"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mindspark"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        viewBinding = true
    }
}

//dependencies {
//
//    // AndroidX
//    implementation(libs.androidx.credentials)
//
//    // Android 13 and below.
//    implementation(libs.androidx.credentials.play.services.auth)
//    implementation(libs.androidx.core.ktx.v190)
//
//    // Firebase
//    implementation(libs.google.firebase.auth.ktx)
//    implementation(libs.firebase.auth.ktx)
//    implementation(platform(libs.google.firebase.bom))
//
//    // Google Play Services
//    implementation(libs.play.services.auth.v2041)
//
//    // AndroidX
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.androidx.constraintlayout)
//    implementation(libs.androidx.lifecycle.livedata.ktx)
//    implementation(libs.androidx.lifecycle.viewmodel.ktx)
//    implementation(libs.androidx.navigation.fragment.ktx)
//    implementation(libs.androidx.navigation.ui.ktx)
//
//    // Jetpack Compose
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//
//    // Material
//    implementation(libs.material)
//    implementation(libs.googleid)
//    implementation(libs.androidx.espresso.core)
//    implementation(libs.androidx.transition)
//    implementation(libs.navigation.compose)
//    implementation(libs.androidx.transition)
//
//    // Testing
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//
//    // Debug
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//
////    implementation(libs.material3)
////    implementation(libs.androidx.material.icons.core)
////    implementation(libs.androidx.material.icons.extended)
//
//}

dependencies {

    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.google.android.gms:play-services-auth:20.4.1")
    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation("com.google.accompanist:accompanist-swiperefresh:0.28.0")



    // Material Components
    implementation(libs.material)

    // Material Icons for Compose
    implementation("androidx.compose.material3:material3:1.0.1")
    implementation("androidx.compose.material:material-icons-core:1.5.0")
    implementation("androidx.compose.material:material-icons-extended:1.5.0")

    // Android Credentials
    implementation(libs.androidx.credentials)

    // Firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.auth.ktx)

    // Google Play Services
    implementation(libs.play.services.auth.v2041)
    implementation(libs.navigation.compose)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.firestore)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Debugging
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.0-alpha")

}
