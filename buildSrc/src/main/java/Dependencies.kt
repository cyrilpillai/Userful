object Versions {
    const val minSdk = 16
    const val compileSdk = 27

    const val buildTools = "27.0.3"
    const val androidSupport = "27.1.1"
    const val gradlePlugin = "3.1.2"
    const val kotlin = "1.2.30"
    const val rxJava = "2.1.13"
    const val rxAndroid = "2.0.2"
    const val constraintLayout = "1.1.0"
    const val archComp = "1.1.1"
    const val room = "1.0.0"
    const val dagger = "2.15"
    const val retrofit = "2.4.0"
    const val retrofitRxAdapter = "1.0.0"
    const val okHttp = "3.9.0"
    const val picasso = "2.5.2"
    const val okHttpDownloader = "1.1.0"
    const val stetho = "1.5.0"
    const val jUnit = "4.12"
    const val supportRunner = "1.0.2"
    const val espresso = "3.0.2"
}

object Deps {
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlin}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val supportAppCompat = "com.android.support:appcompat-v7:${Versions.androidSupport}"
    const val supportRecyclerView = "com.android.support:recyclerview-v7:${Versions.androidSupport}"
    const val supportConstraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"
    const val lifeCycle = "android.arch.lifecycle:extensions:${Versions.archComp}"
    const val kaptLifeCycle = "android.arch.lifecycle:compiler:${Versions.archComp}"
    const val room = "android.arch.persistence.room:runtime:${Versions.room}"
    const val roomRx = "android.arch.persistence.room:rxjava2:${Versions.room}"
    const val kaptRoom = "android.arch.persistence.room:compiler:${Versions.room}"
    const val dagger = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val kaptDagger = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitRxAdapter = "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.retrofitRxAdapter}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    const val picassoOkhttpDownloader = "com.jakewharton.picasso:picasso2-okhttp3-downloader:${Versions.okHttpDownloader}"
    const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    const val stethoNetworkInterceptor = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"

    const val testJUnit = "junit:junit:${Versions.jUnit}"
    const val testSupportRunner = "com.android.support.test:runner:${Versions.supportRunner}"
    const val testEspressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    const val testLiveData = "android.arch.core:core-testing:${Versions.archComp}"
    const val testRoom = "android.arch.persistence.room:testing:${Versions.room}"
}