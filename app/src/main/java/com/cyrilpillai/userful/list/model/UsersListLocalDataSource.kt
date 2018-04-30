package com.cyrilpillai.userful.list.model

import com.cyrilpillai.userful.db.dao.UserDao
import com.cyrilpillai.userful.db.entity.User
import io.reactivex.Flowable

class UsersListLocalDataSource(private val userDao: UserDao) : UsersListContract.LocalDataSource {

    override fun insertUsers(users: List<User>) = userDao.upsert(users)

    override fun deleteUsers() {
    }

    override fun fetchUsers(): Flowable<List<User>> = userDao.getAll()
}