package com.cyrilpillai.userful.list.model

import android.util.Log
import com.cyrilpillai.userful.extensions.*
import com.cyrilpillai.userful.list.UserEntity
import com.cyrilpillai.userful.networking.entity.Outcome
import com.cyrilpillai.userful.networking.schedulers.Scheduler
import com.cyrilpillai.userful.utils.Constants
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class UsersListRepository(
        private val localDataSource: UsersListContract.LocalDataSource,
        private val remoteDataSource: UsersListContract.RemoteDataSource,
        private val scheduler: Scheduler,
        private val compositeDisposable: CompositeDisposable) : UsersListContract.Repository {

    override val usersOutcome: BehaviorSubject<Outcome<List<UserEntity>>> = BehaviorSubject.create()

    override fun fetchUsers(forceUpdate: Boolean) {
        Log.d(Constants.APP_NAME, "fetchUsers: ")
        usersOutcome.loading(true)
        if (forceUpdate) fetchUsersFromRemote()
        localDataSource.fetchUsers()
                .performOnBackOutOnMain(scheduler)
                .subscribe(
                        { response ->
                            if (response.isNotEmpty()) {
                                usersOutcome.success(mutableListOf<UserEntity>().apply {
                                    response.forEach {
                                        add(UserEntity(
                                                id = it.uid,
                                                name = "${it.name.firstName.capitalize()} ${it.name.lastName.capitalize()}",
                                                location = "${it.location.city.capitalize()}, ${it.location.state.capitalize()}",
                                                profileImage = it.picture.thumbnail))
                                    }
                                })
                            } else {
                                fetchUsersFromRemote()
                            }
                        },
                        { error -> fetchUsersFromRemote() }
                )
                .addTo(compositeDisposable)
    }

    private fun fetchUsersFromRemote() {
        Log.d(Constants.APP_NAME, "fetchUsersFromRemote: ")
        remoteDataSource.fetchUsers()
                .performOnBackOutOnMain(scheduler)
                .subscribe(
                        {
                            Single.just(it)
                                    .performOnBack(scheduler)
                                    .subscribe { data ->
                                        localDataSource.deleteAllUsers()
                                        localDataSource.insertUsers(data)
                                    }
                                    .dispose()
                        },
                        { error -> usersOutcome.failed(error) }
                )
                .addTo(compositeDisposable)
    }
}