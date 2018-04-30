package com.cyrilpillai.userful.networking.entity

import com.cyrilpillai.userful.db.entity.User
import com.google.gson.annotations.SerializedName

data class ApiEnvelope(
        @SerializedName("results") val users: List<User>
)