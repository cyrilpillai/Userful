package com.cyrilpillai.userful.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.cyrilpillai.userful.db.dao.UserDao
import com.cyrilpillai.userful.db.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserfulDb : RoomDatabase() {

    object Constants {
        const val DATABASE_NAME = "userful"
    }

    abstract fun userDao(): UserDao
}
