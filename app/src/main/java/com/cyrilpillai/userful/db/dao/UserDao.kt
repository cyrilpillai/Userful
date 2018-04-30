package com.cyrilpillai.userful.db.dao

import android.arch.persistence.room.*
import com.cyrilpillai.userful.db.entity.User
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(users: List<User>)

    @Query("SELECT * FROM users")
    fun getAll(): Flowable<List<User>>

    @Query("SELECT * FROM users WHERE uid=:id")
    fun getUserDetails(id: Long): Flowable<User>
}