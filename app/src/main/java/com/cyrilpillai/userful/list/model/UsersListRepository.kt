package com.cyrilpillai.userful.list.model

import com.cyrilpillai.userful.extensions.*
import com.cyrilpillai.userful.list.UserEntity
import com.cyrilpillai.userful.networking.entity.Outcome
import com.cyrilpillai.userful.networking.schedulers.Scheduler
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class UsersListRepository(
        private val localDataSource: UsersListContract.LocalDataSource,
        private val remoteDataSource: UsersListContract.RemoteDataSource,
        private val scheduler: Scheduler,
        private val compositeDisposable: CompositeDisposable) : UsersListContract.Repository {

    override val usersOutcome: BehaviorSubject<Outcome<List<UserEntity>>> = BehaviorSubject.create()

    override fun fetchUsers(forceUpdate: Boolean) {
        usersOutcome.loading(true)
        if (forceUpdate) fetchUsersFromRemote()
        localDataSource.fetchUsers()
                .timeout(1, TimeUnit.SECONDS)
                .onErrorResumeNext(Flowable.empty())
                .flatMapIterable { it }
                .map {
                    UserEntity(
                            id = it.uid,
                            name = "${it.name.firstName} ${it.name.lastName}",
                            profileImage = it.picture.thumbnail)
                }
                .toList()
                .performOnBackOutOnMain(scheduler)
                .subscribe(
                        { response ->
                            usersOutcome.success(response)
                            /*usersOutcome.success(mutableListOf<UserEntity>().apply {
                                response.forEach {
                                    add(UserEntity(
                                            it.uid,
                                            "${it.name.firstName} ${it.name.lastName}",
                                            it.picture.thumbnail))
                                }
                            })*/
                        },
                        { error -> fetchUsersFromRemote() }
                )
                .addTo(compositeDisposable)
    }

    private fun fetchUsersFromRemote() {
        remoteDataSource.fetchUsers()
                .performOnBack(scheduler)
                .subscribe(
                        { response ->
                            localDataSource.deleteUsers()
                            localDataSource.insertUsers(response)
                        },
                        { error -> error.printStackTrace()/*usersOutcome.onError(error)*/ }
                )
                .addTo(compositeDisposable)
    }
}