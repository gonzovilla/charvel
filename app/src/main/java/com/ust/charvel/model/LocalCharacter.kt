package com.ust.charvel.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters_table")
data class LocalCharacter(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val characterId: Long,
    @SerializedName("name") var name: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("image_url") var imgUrl: String,
    @SerializedName("comics") val comics: Int,
    @SerializedName("series") val series: Int,
    @SerializedName("stories") val stories: Int,
    @SerializedName("events") val events: Int
)
