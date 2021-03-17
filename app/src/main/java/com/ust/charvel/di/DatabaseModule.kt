package com.ust.charvel.di

import android.app.Application
import androidx.room.Room
import com.ust.charvel.db.CharacterDao
import com.ust.charvel.db.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): CharacterDatabase =
        Room.databaseBuilder(app, CharacterDatabase::class.java, "character-db")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCharacterDao(db: CharacterDatabase): CharacterDao = db.characterDao()

}
