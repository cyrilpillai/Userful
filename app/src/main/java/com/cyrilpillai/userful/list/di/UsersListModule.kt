package com.cyrilpillai.userful.list.di

import com.cyrilpillai.userful.db.dao.UserDao
import com.cyrilpillai.userful.list.model.UsersListContract
import com.cyrilpillai.userful.list.model.UsersListLocalDataSource
import com.cyrilpillai.userful.list.model.UsersListRemoteDataSource
import com.cyrilpillai.userful.list.model.UsersListRepository
import com.cyrilpillai.userful.list.view.adapter.UserAdapter
import com.cyrilpillai.userful.list.viewmodel.UsersListViewModelFactory
import com.cyrilpillai.userful.networking.schedulers.Scheduler
import com.cyrilpillai.userful.networking.service.ApiService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class UsersListModule {
    @Provides
    fun providesUsersListViewModelFactory(
            repository: UsersListContract.Repository,
            compositeDisposable: CompositeDisposable
    ): UsersListViewModelFactory = UsersListViewModelFactory(repository, compositeDisposable)

    @Provides
    fun providesUsersListRepository(
            localDataSource: UsersListContract.LocalDataSource,
            remoteDataSource: UsersListContract.RemoteDataSource,
            scheduler: Scheduler,
            compositeDisposable: CompositeDisposable
    ): UsersListContract.Repository =
            UsersListRepository(localDataSource, remoteDataSource, scheduler, compositeDisposable)


    @Provides
    fun providesUsersListLocalDataSource(userDao: UserDao): UsersListContract.LocalDataSource =
            UsersListLocalDataSource(userDao)

    @Provides
    fun providesUsersListRemoteDataSource(apiService: ApiService): UsersListContract.RemoteDataSource =
            UsersListRemoteDataSource(apiService)


    @Provides
    fun providesCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun providesUserAdapter(): UserAdapter = UserAdapter()
}