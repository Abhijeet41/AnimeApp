package com.abhi41.planet.domain.model

data class PlanetInfoWithCharacters(
    val characters: List<Character>,
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val isDestroed: Boolean

)
