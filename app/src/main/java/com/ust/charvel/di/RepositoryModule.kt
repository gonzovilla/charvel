package com.ust.charvel.di

import com.ust.charvel.domain.CharacterRepository
import com.ust.charvel.domain.ICharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {
    @Binds
    fun it(it: CharacterRepository): ICharacterRepository
}