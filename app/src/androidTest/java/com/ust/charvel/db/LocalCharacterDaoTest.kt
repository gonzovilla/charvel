package com.ust.charvel.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ust.charvel.model.LocalCharacter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalCharacterDaoTest : BaseDaoTest<CharacterDatabase>(CharacterDatabase::class.java) {

    @Test
    @Throws(InterruptedException::class)
    fun insertAndGetCharacters(): Unit = runBlocking {
        // GIVEN
        val localCharacters = listOf(
            LocalCharacter(1, "Character 1", "Description 1", "http://1.jpg", 1, 2, 3, 4),
            LocalCharacter(2, "Character 2", "Description 2", "http://2.jpg", 5, 6, 7, 8)
        )
        db.characterDao().insertCharacters(localCharacters)

        // THEN
        assertEquals(localCharacters[0], db.characterDao().getAllLocalCharacters().first().toList()[0])
        assertEquals(localCharacters[0], db.characterDao().getCharacterById(1).first())
    }

}