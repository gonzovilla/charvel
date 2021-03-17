package com.ust.charvel.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ust.charvel.domain.ICharacterRepository
import com.ust.charvel.model.LocalCharacter
import com.ust.charvel.network.Status

class CharacterViewModel @ViewModelInject constructor(
    private val characterRepository: ICharacterRepository
) : ViewModel() {

    fun getCharacterList(offset: Int): LiveData<Status<List<LocalCharacter>>> =
        characterRepository.getCharacters(offset).asLiveData()

    fun getCharacterDetail(characterId: Long): LiveData<Status<LocalCharacter>> =
        characterRepository.getCharacterById(characterId).asLiveData()

}