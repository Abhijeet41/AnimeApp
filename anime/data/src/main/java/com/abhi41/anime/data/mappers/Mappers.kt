package com.abhi41.anime.data.mappers

import com.abhi41.anime.domain.models.Charecter
import com.abhi41.anime.domain.models.CharecterDetails
import com.abhi41.anime.domain.models.OriginPlanet
import com.abhi41.anime.domain.models.Transformation
import com.abhi41.planet.core_network.dtos.characters.CharectorDto
import com.abhi41.planet.core_network.dtos.charectorDetails.CharectorDetailsResponse
import com.abhi41.planet.core_network.dtos.charectorDetails.OriginPlanetDto
import com.abhi41.planet.core_network.dtos.charectorDetails.TransformationDto

fun List<CharectorDto>.toCharecters(): List<Charecter> {
   return map {
        Charecter(
            id = it.id,
            name = it.name,
            image = it.image
        )
    }
}

fun CharectorDetailsResponse.toDomain(): CharecterDetails{
    return CharecterDetails(
        affiliation = affiliation,
        description = description,
        gender = gender,
        id = id,
        image = image,
        ki = ki,
        maxKi = maxKi,
        name = name,
        originPlanet = originPlanet.toDomain(),
        race = race,
        transformations = transformations.toDomain()
    )
}

fun OriginPlanetDto.toDomain(): OriginPlanet {
    return OriginPlanet(
        id = id,
        name = name,
        image = image,
        description = description
    )
}

fun List<TransformationDto>.toDomain(): List<Transformation> {
    return map {
        Transformation(
            id = it.id,
            image = it.image,
            ki = it.ki,
            name = it.name
        )
    }
}