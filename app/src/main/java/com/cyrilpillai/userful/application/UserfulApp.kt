package com.cyrilpillai.userful.application

import android.app.Activity
import android.app.Application
import com.cyrilpillai.userful.application.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class UserfulApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var picasso: Picasso

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
        //To make sure Picasso (with Okhttp3Downloader) is initialized only once
        Picasso.setSingletonInstance(picasso)
        Stetho.initializeWithDefaults(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}