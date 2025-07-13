package com.abhi41.planet.core_network.dtos.charectorDetails


import com.google.gson.annotations.SerializedName

data class CharectorDetailsResponse(
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
    @SerializedName("originPlanet")
    val originPlanet: OriginPlanetDto,
    @SerializedName("race")
    val race: String,
    @SerializedName("transformations")
    val transformations: List<TransformationDto>
)