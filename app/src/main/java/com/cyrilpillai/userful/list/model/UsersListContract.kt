package com.cyrilpillai.userful.list.model

import com.cyrilpillai.userful.db.entity.User
import com.cyrilpillai.userful.list.UserEntity
import com.cyrilpillai.userful.networking.entity.Outcome
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

interface UsersListContract {
    interface Repository {
        val usersOutcome: BehaviorSubject<Outcome<List<UserEntity>>>
        fun fetchUsers(forceUpdate: Boolean = false)
    }

    interface LocalDataSource {
        fun insertUsers(users: List<User>)
        fun fetchUsers(): Flowable<List<User>>
        fun deleteUsers()
    }

    interface RemoteDataSource {
        fun fetchUsers(): Single<List<User>>
    }
}