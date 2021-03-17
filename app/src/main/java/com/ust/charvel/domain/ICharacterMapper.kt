package com.ust.charvel.domain

import com.ust.charvel.model.LocalCharacter
import com.ust.charvel.model.RemoteCharacter
import com.ust.charvel.utils.Mapper

interface ICharacterMapper : Mapper<LocalCharacter, RemoteCharacter> {

    override fun RemoteCharacter.toStorage(): LocalCharacter {
        return LocalCharacter(
            characterId = id,
            name = name,
            description = description,
            imgUrl = thumbnail.path + "." + thumbnail.extension,
            comics = comics.available,
            series = series.available,
            stories = stories.available,
            events = events.available
        )
    }

}