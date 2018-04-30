package com.cyrilpillai.userful.networking.service

import com.cyrilpillai.userful.networking.entity.ApiEnvelope
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("api/")
    fun getUsers(@QueryMap queryParams: Map<String, String>): Single<ApiEnvelope>
}