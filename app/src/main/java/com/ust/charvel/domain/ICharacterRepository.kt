package com.ust.charvel.domain

import com.ust.charvel.model.CharacterListResponse
import com.ust.charvel.model.LocalCharacter
import com.ust.charvel.network.Status
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ICharacterRepository {

    fun getCharacters(offset: Int): Flow<Status<List<LocalCharacter>>>

    suspend fun getCharactersFromNetwork(offset: Int): Response<CharacterListResponse>

    fun getCharacterById(characterId: Long): Flow<Status<LocalCharacter>>

    suspend fun getCharacterByIdFromNetwork(id: Long): Response<CharacterListResponse>
}
