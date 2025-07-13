package com.abhi41.planet.core_network.dtos.characters


import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("items")
    val items: List<CharectorDto>,
    )