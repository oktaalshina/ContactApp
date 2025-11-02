plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp) // Plugin KSP untuk Room compiler
}

android {
    namespace = "com.example.contactapp" // Menggunakan contactapp sesuai struktur folder
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.contactapp"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Dependensi Utama Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //  Room Database
    implementation(libs.room.runtime) // wajib
    implementation(libs.room.ktx) // direkomendasikan utk Kotlin (suspend/Flow)
    ksp(libs.room.compiler) // wajib: codegen DAO via KSP

    // Coroutines
    implementation(libs.coroutines.android)

    // ifecycle Components (FIX untuk 'viewModelScope' merah)
    // Dependency ini menyediakan viewModelScope dan ViewModel Factory
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
}