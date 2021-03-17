package com.ust.charvel.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ust.charvel.model.LocalCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(localCharacters: List<LocalCharacter>)

    @Query("SELECT * FROM characters_table WHERE characterId = :id")
    fun getCharacterById(id: Long): Flow<LocalCharacter>

    @Query("SELECT * FROM characters_table ORDER BY name")
    fun getAllLocalCharacters(): Flow<List<LocalCharacter>>

    @Query("DELETE FROM characters_table")
    fun clearAllCharacters()

}