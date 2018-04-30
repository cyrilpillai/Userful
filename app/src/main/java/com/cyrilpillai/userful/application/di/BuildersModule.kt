package com.cyrilpillai.userful.application.di

import com.cyrilpillai.userful.list.view.UsersListActivity
import com.cyrilpillai.userful.list.di.UsersListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector(modules = [UsersListModule::class])
    abstract fun bindUsersListActivity(): UsersListActivity
}