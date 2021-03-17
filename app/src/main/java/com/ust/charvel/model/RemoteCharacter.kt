package com.ust.charvel.model

import com.google.gson.annotations.SerializedName

data class RemoteCharacter(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("thumbnail") var thumbnail: Thumbnail,
    @SerializedName("comics") var comics: AppearsOn,
    @SerializedName("series") var series: AppearsOn,
    @SerializedName("stories") var stories: AppearsOn,
    @SerializedName("events") var events: AppearsOn
)

data class Thumbnail(
    @SerializedName("path") var path: String,
    @SerializedName("extension") var extension: String
)

data class AppearsOn(
    @SerializedName("available") var available: Int
)