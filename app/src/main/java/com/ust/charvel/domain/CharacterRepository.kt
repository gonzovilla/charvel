package com.ust.charvel.domain

import com.ust.charvel.db.CharacterDao
import com.ust.charvel.model.CharacterListResponse
import com.ust.charvel.model.LocalCharacter
import com.ust.charvel.network.ApiService
import com.ust.charvel.network.Status
import com.ust.charvel.utils.md5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val characterDao: CharacterDao,
    private val apiService: ApiService
) : ICharacterRepository, ICharacterMapper {

    companion object {
        private const val PUBLIC_KEY = "3e67cad8c047dd081c8c713b4ee2c4fe"
        private const val PRIVATE_KEY = "e54c9adf1d1da8118a4cb59842e66c8a2db0710c"
    }

    override fun getCharacters(offset: Int): Flow<Status<List<LocalCharacter>>> = flow {
        emit(Status.loading())

        val remoteCharacters = getCharactersFromNetwork(offset)
        remoteCharacters.body()?.data?.charactersList?.toStorage()?.let { characterDao.insertCharacters(it) }

        val localCharacters = characterDao.getAllLocalCharacters()
        localCharacters.collect { list ->
            if (list.isEmpty() && !remoteCharacters.isSuccessful) {
                emit(Status.error<List<LocalCharacter>>(""))
            } else {
                emit(Status.success(list))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCharactersFromNetwork(offset: Int): Response<CharacterListResponse> {
        return try {
            val timestamp = System.currentTimeMillis()
            apiService.getCharacters(offset, PUBLIC_KEY, timestamp, generateHash(timestamp))
        } catch (e: Exception) {
            Response.error(404, "".toResponseBody(null))
        }
    }

    override fun getCharacterById(characterId: Long): Flow<Status<LocalCharacter>> = flow {
        emit(Status.loading())

        val remoteCharacterResponse = getCharacterByIdFromNetwork(characterId)
        if (remoteCharacterResponse.isSuccessful) {
            val remoteCharacter = remoteCharacterResponse.body()?.data?.charactersList?.get(0)
            if (remoteCharacter != null) {
                emit(Status.success(remoteCharacter.toStorage()))
            } else {
                emit(Status.error<LocalCharacter>(""))
            }
        } else {
            val localCharacter = characterDao.getCharacterById(characterId)
            emitAll(localCharacter.map { Status.success(it) })
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCharacterByIdFromNetwork(id: Long): Response<CharacterListResponse> {
        return try {
            val timestamp = System.currentTimeMillis()
            apiService.getCharacterDetail(id, PUBLIC_KEY, timestamp, generateHash(timestamp))
        } catch (e: Exception) {
            Response.error(404, "".toResponseBody(null))
        }
    }

    private fun generateHash(timestamp: Long): String {
        val stringToHash = String.format(Locale.getDefault(), "$timestamp$PRIVATE_KEY$PUBLIC_KEY")
        return stringToHash.md5()
    }

}