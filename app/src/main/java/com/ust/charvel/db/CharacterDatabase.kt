package com.ust.charvel.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ust.charvel.model.LocalCharacter

@Database(entities = [LocalCharacter::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

}