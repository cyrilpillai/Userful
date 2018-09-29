package com.cyrilpillai.userful.db.dao

import android.arch.persistence.room.*
import com.cyrilpillai.userful.db.entity.User
import io.reactivex.Flowable

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsert(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsert(users: List<User>): List<Long>

    @Query("SELECT * FROM users")
    abstract fun getAll(): Flowable<List<User>>

    @Query("SELECT * FROM users WHERE uid=:id")
    abstract fun getUserDetails(id: Long): Flowable<User>

    @Query("DELETE FROM users")
    abstract fun truncate()

    @Transaction
    open fun insertUsers(users: List<User>): List<Long> {
        truncate()
        return upsert(users)
    }
}