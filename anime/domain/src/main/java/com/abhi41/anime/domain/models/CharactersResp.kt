package com.abhi41.anime.domain.models

data class CharactersResp(

    val items: List<Character>,
    val meta: Meta,
    val links: Links

)
data class Meta(
    val totalItems: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalPages: Int,
    val currentPage: Int
)

data class Links(
    val first: String,
    val previous: String?,
    val next: String?,
    val last: String
)