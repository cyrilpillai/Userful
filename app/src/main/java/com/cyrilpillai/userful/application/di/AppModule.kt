package com.cyrilpillai.userful.application.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.cyrilpillai.userful.db.UserfulDb
import com.cyrilpillai.userful.db.dao.UserDao
import com.cyrilpillai.userful.networking.schedulers.AppScheduler
import com.cyrilpillai.userful.networking.schedulers.Scheduler
import com.cyrilpillai.userful.networking.service.ApiService
import com.cyrilpillai.userful.utils.Constants
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.picasso.OkHttp3Downloader
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun providesContext(application: Application): Context = application


    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesScheduler(): Scheduler = AppScheduler()


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient,
                         gsonConverterFactory: GsonConverterFactory,
                         rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit =
            Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .client(okHttpClient)
                    .build()


    @Provides
    @Singleton
    fun providesOkHttpClient(cache: Cache, stethoInterceptor: StethoInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .cache(cache)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addNetworkInterceptor(stethoInterceptor)
                    .build()

    @Provides
    @Singleton
    fun providesOkhttpCache(context: Context): Cache =
            Cache(context.cacheDir, 10 * 1024 * 1024)


    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providesRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory =
            RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun providesStethoInterceptor(): StethoInterceptor = StethoInterceptor()

    @Provides
    @Singleton
    fun providesOkhttp3Downloader(okHttpClient: OkHttpClient): OkHttp3Downloader =
            OkHttp3Downloader(okHttpClient)

    @Provides
    @Singleton
    fun providesPicasso(context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso =
            Picasso.Builder(context)
                    .downloader(okHttp3Downloader)
                    .build()

    @Provides
    @Singleton
    fun providesDatabase(context: Context): UserfulDb =
            Room.databaseBuilder(context,
                    UserfulDb::class.java,
                    UserfulDb.Constants.DATABASE_NAME)
                    .build()

    @Provides
    @Singleton
    fun providesUserDao(userfulDb: UserfulDb): UserDao = userfulDb.userDao()
}