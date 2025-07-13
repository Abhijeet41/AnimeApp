package com.abhi41.planet.core_network.dtos.charectorDetails


import com.google.gson.annotations.SerializedName

data class Transformation(
    @SerializedName("deletedAt")
    val deletedAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("ki")
    val ki: String,
    @SerializedName("name")
    val name: String
)