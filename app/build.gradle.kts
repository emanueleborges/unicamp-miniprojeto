plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    jacoco
}

// Configuração de Java Toolchain FORA do bloco android
kotlin {
    jvmToolchain(21)
}

android {
    namespace = "com.example.miniprojeto"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.miniprojeto"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("../sensor-monitor-release.jks")
            storePassword = "SensorMonitor2026"
            keyAlias = "sensor-monitor"
            keyPassword = "SensorMonitor2026"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco"))
    }

    val jacocoExcludes = listOf(
        // AndroidX / bibliotecas externas
        "**/androidx/**",
        // Android generated
        "**/R.class", "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        // Hilt / DI generated
        "**/*_Hilt*.*", "**/Hilt_*.*",
        "**/*_Factory.*", "**/*_MembersInjector.*",
        "**/*Module.*", "**/*Module\$*.*",
        "**/*Directions*.*", "**/*Args*.*",
        "**/*_GeneratedInjector*.*",
        "**/*_ComponentTreeDeps*.*",
        "**/di/**",
        "**/dagger/**",
        "**/hilt_aggregated_deps/**",
        // Compose generated singletons
        "**/ComposableSingletons*.*",
        // UI / Compose (não testável em unit test)
        "**/ui/theme/**",
        "**/ui/main/**",
        "**/SensorMonitorApp.*",
        "**/presentation/screen/main/**",
        "**/presentation/screen/accelerometer/AccelerometerScreen*",
        "**/presentation/screen/light/LightSensorScreen*",
        "**/presentation/screen/history/HistoryScreen*",
        "**/presentation/components/**",
        "**/presentation/navigation/**",
        // Android-dependent (SQLite, Context, SensorEvent)
        "**/data/database/**",
        "**/data/repository/**",
        "**/sensor/BaseSensorHandler*",
        "**/sensor/ShakeDetector*",
        "**/sensor/AccelerometerHandler.class",
        "**/sensor/LightSensorHandler.class",
        "**/util/VibrationHelper*",
        "**/presentation/screen/accelerometer/AccelerometerViewModel*"
    )

    val kotlinClasses = fileTree(layout.buildDirectory.dir("intermediates/classes/debug/transformDebugClassesWithAsm/dirs")) {
        exclude(jacocoExcludes)
    }

    val kotlinCompiledClasses = fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
        exclude(jacocoExcludes)
    }

    classDirectories.setFrom(kotlinClasses + kotlinCompiledClasses)

    sourceDirectories.setFrom(
        files("src/main/java")
    )

    executionData.setFrom(
        fileTree(layout.buildDirectory) {
            include("jacoco/testDebugUnitTest.exec")
        }
    )
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}