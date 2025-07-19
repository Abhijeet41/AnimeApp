package com.abhi41.anime.data.mappers

import com.abhi41.anime.domain.models.Character
import com.abhi41.anime.domain.models.CharacterDetails
import com.abhi41.anime.domain.models.OriginPlanet
import com.abhi41.anime.domain.models.Transformation
import com.abhi41.planet.core_network.dtos.characters.CharectorDto
import com.abhi41.planet.core_network.dtos.charectorDetails.CharectorDetailsResponse
import com.abhi41.planet.core_network.dtos.charectorDetails.OriginPlanetDto
import com.abhi41.planet.core_network.dtos.charectorDetails.TransformationDto

fun List<CharectorDto>.toDomainCharecters(): List<Character> {
   return map {
        Character(
            id = it.id,
            name = it.name,
            image = it.image
        )
    }
}

fun CharectorDetailsResponse.toDomainCharacterDetails(): CharacterDetails{
    return CharacterDetails(
        affiliation = affiliation,
        description = description,
        gender = gender,
        id = id,
        image = image,
        ki = ki,
        maxKi = maxKi,
        name = name,
        originPlanet = originPlanet.toDomainOriginPlanet(),
        race = race,
        transformations = transformations.toDomainTransformation()
    )
}

fun OriginPlanetDto.toDomainOriginPlanet(): OriginPlanet {
    return OriginPlanet(
        id = id,
        name = name,
        image = image,
        description = description
    )
}

fun List<TransformationDto>.toDomainTransformation(): List<Transformation> {
    return map {
        Transformation(
            id = it.id,
            image = it.image,
            ki = it.ki,
            name = it.name
        )
    }
}