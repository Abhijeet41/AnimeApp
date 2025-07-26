package com.abhi41.planet.core_network.dtos.characters


import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("items")
    val items: List<CharectorDto>,
    val meta: MetaDto,
    val links: LinksDto
)

data class MetaDto(
    val totalItems: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalPages: Int,
    val currentPage: Int
)

data class LinksDto(
    val first: String,
    val previous: String?,
    val next: String?,
    val last: String
)