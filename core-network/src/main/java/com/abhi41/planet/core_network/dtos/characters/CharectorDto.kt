package com.abhi41.planet.core_network.dtos.characters


import com.google.gson.annotations.SerializedName

data class CharectorDto(
    @SerializedName("affiliation")
    val affiliation: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("ki")
    val ki: String,
    @SerializedName("maxKi")
    val maxKi: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("race")
    val race: String
)