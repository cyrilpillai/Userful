package com.cyrilpillai.userful.db.entity

import com.google.gson.annotations.SerializedName

data class Name(
        @SerializedName("title") val salutation: String,
        @SerializedName("first") val firstName: String,
        @SerializedName("last") val lastName: String
)