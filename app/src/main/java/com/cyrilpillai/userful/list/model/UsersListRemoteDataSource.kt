package com.cyrilpillai.userful.list.model

import com.cyrilpillai.userful.db.entity.User
import com.cyrilpillai.userful.networking.service.ApiService
import com.cyrilpillai.userful.utils.Constants
import io.reactivex.Single

class UsersListRemoteDataSource(private val apiService: ApiService) :
        UsersListContract.RemoteDataSource {
    override fun fetchUsers(): Single<List<User>> {
        return apiService.getUsers(
                mapOf("seed" to Constants.APP_NAME, "results" to Constants.RESULT_LIMIT))
                .map { it.users }
    }
}