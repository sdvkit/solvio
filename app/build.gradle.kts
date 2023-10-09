plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.sdv.kit.solvio"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.sdv.kit.solvio"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    val gpsAuthVersion = "20.7.0"
    val splashScreenVersion = "1.0.1"
    val glideVersion = "4.16.0"
    val gsonVersion = "2.10.1"

    // BCrypt
    implementation("org.mindrot:jbcrypt:0.4")

    // Gson
    implementation("com.google.code.gson:gson:$gsonVersion")

    // Glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:$splashScreenVersion")

    // Firebase Realtime-Database
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")

    // Google Play Services Auth (Sign in with Google)
    implementation("com.google.android.gms:play-services-auth:$gpsAuthVersion")

    // Core
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}