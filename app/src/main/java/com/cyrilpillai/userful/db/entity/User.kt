package com.cyrilpillai.userful.db.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
        @PrimaryKey(autoGenerate = true) val uid: Long,
        @SerializedName("name") @Embedded(prefix = "name_") val name: Name,
        @SerializedName("email") val email: String = "",
        @SerializedName("phone") val phone: String = "",
        @SerializedName("cell") val cell: String = "",
        @SerializedName("gender") val gender: String = "",
        @SerializedName("nat") val nationality: String = "",
        @SerializedName("dob") @Embedded(prefix = "dob_") val dob: DateAndAge,
        @SerializedName("registered") @Embedded(prefix = "registered_date_") val registeredDate: DateAndAge,
        @SerializedName("picture") @Embedded(prefix = "picture_") val picture: Picture,
        @SerializedName("location") @Embedded(prefix = "location_") val location: Location
)
