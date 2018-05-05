package com.cyrilpillai.userful

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.cyrilpillai.userful.db.UserfulDb
import com.cyrilpillai.userful.db.dao.UserDao
import com.cyrilpillai.userful.db.entity.User
import com.cyrilpillai.userful.networking.entity.ApiEnvelope
import com.cyrilpillai.userful.testing.DependencyProvider
import com.google.gson.Gson
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userfulDb: UserfulDb
    private lateinit var userDao: UserDao

    private val gson: Gson by lazy { Gson() }
    private val USERS_LIST: List<User> by lazy {
        gson.fromJson(
                DependencyProvider.getResponseFromJson("users"),
                ApiEnvelope::class.java).users
    }

    @Before
    fun initDb() {
        userfulDb = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                UserfulDb::class.java)
                .allowMainThreadQueries()
                .build()
        userDao = userfulDb.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        userfulDb.close()
    }

    @Test
    fun insertUser() {
        userDao.upsert(USERS_LIST[0])
        userDao.getAll().test().apply {
            assertNoErrors()
            assertValueCount(1)
            Assert.assertEquals(1, values()[0].size)
        }
    }


    @Test
    fun insertUsers() {
        userDao.upsert(USERS_LIST)
        userDao.getAll().test().apply {
            assertNoErrors()
            assertValueCount(1)
            Assert.assertEquals(10, values()[0].size)
        }
    }

    @Test
    fun getUserDetails() {
        val user = USERS_LIST[0]
        val uid: Long = userDao.upsert(user)
        userDao.getUserDetails(uid).test().apply {
            assertNoErrors()
            assertValueCount(1)
            Assert.assertEquals("elizabeth.owens@example.com", values()[0].email)
            Assert.assertEquals("elizabeth", values()[0].name.firstName)
            Assert.assertEquals("wicklow", values()[0].location.city)
        }
    }

    @Test
    fun truncate() {
        userDao.upsert(USERS_LIST)
        userDao.truncate()
        userDao.getAll().test().apply {
            assertNoErrors()
            assertValueCount(1)
            Assert.assertEquals(0, values()[0].size)
        }
    }

    @Test
    fun updateUser() {
        val EXPECTED_GENDER = "male"

        //Insert User & get the auto generated UID
        val user = USERS_LIST[0]
        val uid = userDao.upsert(user)

        //Create a new instance with a few modifications & update the DB based on the UID
        val updatedUser = user.copy(uid = uid, gender = EXPECTED_GENDER)
        userDao.upsert(updatedUser)

        userDao.getUserDetails(uid).test().apply {
            assertNoErrors()
            assertValueCount(1)
            Assert.assertEquals(EXPECTED_GENDER, values()[0].gender)
        }
    }

    @Test
    fun updateUsers() {
        val EXPECTED_NATIONALITY = "IN"

        //Insert Users & get the auto generated UIDs
        val uids: List<Long> = userDao.upsert(USERS_LIST)

        //Create a new List with a few modifications & update the DB based on the UID
        val updatedUserList = mutableListOf<User>()
        USERS_LIST.forEachIndexed { index, user ->
            updatedUserList.add(user.copy(uid = uids[index], nationality = EXPECTED_NATIONALITY))
        }

        userDao.upsert(updatedUserList)

        userDao.getAll().test().apply {
            assertNoErrors()
            assertValueCount(1)
            Assert.assertEquals(10, values()[0].size)
            values()[0].forEach { Assert.assertEquals(EXPECTED_NATIONALITY, it.nationality) }
        }
    }
}