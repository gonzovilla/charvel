package com.ust.charvel.network

import com.ust.charvel.model.CharacterListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") ts: Long,
        @Query("hash") hash: String
    ): Response<CharacterListResponse>

    @GET("characters/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: Long,
        @Query("apikey") apiKey: String,
        @Query("ts") ts: Long,
        @Query("hash") hash: String
    ): Response<CharacterListResponse>

}
