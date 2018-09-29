package com.cyrilpillai.userful.db.entity

import com.google.gson.annotations.SerializedName

data class DateAndAge(
        @SerializedName("date") val date: String,
        @SerializedName("age") val age: Int)