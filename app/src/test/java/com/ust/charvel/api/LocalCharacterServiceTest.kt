package com.ust.charvel.api

import com.ust.charvel.network.ApiService
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException

@RunWith(JUnit4::class)
class LocalCharacterServiceTest : BaseServiceTest() {

    private lateinit var service: ApiService

    @Before
    @Throws(IOException::class)
    fun createService() {
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getRemoteCharacters() = runBlocking {
        enqueueResponse("characters_response.json")
        val charactersList = service.getCharacters(0, "3e67cad8c047dd081c8c713b4ee2c4fe", 1, "2ed30bd9681a517be1264ca438e59113")
            .body()?.data?.charactersList ?: return@runBlocking

        mockWebServer.takeRequest()

        assertThat(charactersList, notNullValue())
        assertThat(charactersList.size, `is`(3))

        val firstCharacter = charactersList[0]
        assertThat(firstCharacter, notNullValue())
        assertThat(firstCharacter.id, `is`(1011334))
        assertThat(firstCharacter.name, `is`("3-D Man"))
        assertThat(firstCharacter.description, `is`(""))
        assertThat(firstCharacter.thumbnail.path, `is`("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784"))
        assertThat(firstCharacter.thumbnail.extension, `is`("jpg"))
        assertThat(firstCharacter.comics.available, `is`(12))
        assertThat(firstCharacter.series.available, `is`(3))
        assertThat(firstCharacter.stories.available, `is`(21))
        assertThat(firstCharacter.events.available, `is`(1))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getRemoteCharacterById() = runBlocking {
        enqueueResponse("character_detail_response.json")
        val character =
            service.getCharacterDetail(1009144, "3e67cad8c047dd081c8c713b4ee2c4fe", 1, "2ed30bd9681a517be1264ca438e59113")
                .body()?.data?.charactersList?.get(0) ?: return@runBlocking

        mockWebServer.takeRequest()

        assertThat(character, notNullValue())

        assertThat(character.id, `is`(1009144))
        assertThat(character.name, `is`("A.I.M."))
        assertThat(character.description, `is`("AIM is a terrorist organization bent on destroying the world."))
        assertThat(character.thumbnail.path, `is`("http://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec"))
        assertThat(character.thumbnail.extension, `is`("jpg"))
        assertThat(character.comics.available, `is`(49))
        assertThat(character.series.available, `is`(33))
        assertThat(character.stories.available, `is`(52))
        assertThat(character.events.available, `is`(0))
    }
}