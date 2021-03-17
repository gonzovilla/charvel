package com.ust.charvel.model

import com.google.gson.annotations.SerializedName

data class CharacterListResponse(
    @SerializedName("data") var data: CharactersData
)

data class CharactersData(
    @SerializedName("results") var charactersList: List<RemoteCharacter>
)