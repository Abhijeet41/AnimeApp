package com.abhi41.planet.data.mappers

import com.abhi41.planet.core_network.dtos.characters.CharectorDto
import com.abhi41.planet.core_network.dtos.planets.Planetresponse
import com.abhi41.planet.domain.model.Character
import com.abhi41.planet.domain.model.PlanetInfoWithCharacters


fun Planetresponse.toDomainPlanetInfoWithCharacters(): PlanetInfoWithCharacters{
    return PlanetInfoWithCharacters(
        characters = characters.toDomaincharacter(),
        description = description,
        id = id,
        image = image,
        name = name,
        isDestroed = isDestroyed
    )

}

fun List<CharectorDto>.toDomaincharacter(): List<Character>{
  return map {
       Character(
           affiliation = it.affiliation,
           description = it.description,
           gender = it.gender,
           id = it.id,
           image = it.image,
           ki = it.ki,
           maxKi = it.maxKi,
           name = it.name,
           race = it.race
       )
   }
}