plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id ("com.google.dagger.hilt.android")
	kotlin("kapt")
	id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
	id("com.google.devtools.ksp")
}

android {
	buildFeatures {
		buildConfig = true
	}
	namespace = "com.example.tasty"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.example.tasty"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
			signingConfig = signingConfigs.getByName("debug")
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
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.10"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

secrets {
	defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
	implementation("androidx.activity:activity-compose:1.8.2")
	implementation(platform("androidx.compose:compose-bom:2024.02.02"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.navigation:navigation-compose:2.7.7")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
	implementation("androidx.compose.material:material-icons-extended")
	//downloadable fonts
	implementation("androidx.compose.ui:ui-text-google-fonts")
	//tracing
	implementation("androidx.tracing:tracing-ktx:1.2.0")
	//hilt
	implementation( "com.google.dagger:hilt-android:2.50")
	annotationProcessor("com.google.dagger:hilt-compiler:2.50")
	implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
	kapt("com.google.dagger:hilt-android-compiler:2.50")
	//coil
	implementation("io.coil-kt:coil-compose:2.5.0")
	implementation("io.coil-kt:coil-svg:2.5.0")
    //html text
    implementation("de.charlex.compose.material3:material3-html-text:2.0.0-beta01")

	//exoplayer
	val mediaVersion = "1.3.0"
	implementation("androidx.media3:media3-exoplayer:$mediaVersion")
	implementation("androidx.media3:media3-ui:$mediaVersion")
	implementation("androidx.media3:media3-common:$mediaVersion")
	implementation("androidx.media3:media3-session:$mediaVersion")
	implementation("androidx.media3:media3-exoplayer-hls:$mediaVersion")

	//moshi
	val moshiVersion = "1.15.0"
	implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
	implementation("com.squareup.moshi:moshi:$moshiVersion")
	ksp("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

	//retrofit
	val retrofitVersion = "2.9.0"
	implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
	implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
	implementation("com.squareup.retrofit2:retrofit-mock:$retrofitVersion")
	//logging interceptors
	implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
	//Room
	val roomVersion = "2.6.1"
	implementation("androidx.room:room-runtime:$roomVersion")
	ksp("androidx.room:room-compiler:$roomVersion")
	implementation("androidx.room:room-ktx:$roomVersion")

	//Mock web server
	implementation("com.squareup.okhttp3:mockwebserver:4.7.2")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
}

// Allow references to generated code
kapt {
	correctErrorTypes = true
}
