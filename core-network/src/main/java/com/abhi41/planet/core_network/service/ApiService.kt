package com.abhi41.planet.core_network.service

import com.abhi41.planet.core_network.dtos.characters.CharactersResponse
import com.abhi41.planet.core_network.dtos.charectorDetails.CharectorDetailsResponse
import com.abhi41.planet.core_network.dtos.planets.Planetresponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //https://dragonball-api.com/api/characters?limit=60//
    @GET("api/characters")
    suspend fun getAllCharacters(
        @Query("limit") limit: Int = 60
    ): CharactersResponse

    //https://dragonball-api.com/api/characters/1
    @GET("api/characters/{id}")
    suspend fun getCharacterDetails(
        @Path("id") id: Int
    ): CharectorDetailsResponse


    //https://dragonball-api.com/api/planets/2

    @GET("api/planets/{id}")
    suspend fun getPlanetDetails(
        @Path("id") id: Int
    ): Planetresponse
}