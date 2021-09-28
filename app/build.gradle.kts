plugins {
    id("com.android.application")
    id("kotlin-android")
    id("io.gitlab.arturbosch.detekt").version("1.18.1")
    id("org.jlleitschuh.gradle.ktlint").version("10.2.0")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "org.rsschool.android2021task5"
        minSdk = 21
        targetSdk = 31
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

detekt {
    toolVersion = "1.18.1"
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    source = files("src/main/java", "src/main/kotlin")
    reports {
        html {
            enabled = true
            destination = file("build/detekt/detekt.html")
        }
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    // Target version of the generated JVM bytecode. It is used for type resolution.
    this.jvmTarget = "1.8"
}

dependencies {

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-beta01")
    implementation("androidx.fragment:fragment-ktx:1.3.6")

    //LOG http
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    //hilt
    val hiltVersion = "2.38.1"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    //Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    //moshi
    val moshiVersion = "1.11.0"
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")

    // Glide
    val glideVersion = "4.11.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    //Paging 3
    val pagingVersion = "3.0.1"
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
