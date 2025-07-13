package com.abhi41.planet.core_network.dtos.planets


import com.abhi41.planet.core_network.dtos.characters.CharectorDto
import com.google.gson.annotations.SerializedName

data class Planetresponse(
    @SerializedName("characters")
    val characters: List<CharectorDto>,
    @SerializedName("deletedAt")
    val deletedAt: Any,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("isDestroyed")
    val isDestroyed: Boolean,
    @SerializedName("name")
    val name: String
)