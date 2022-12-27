package com.example.pokemonapp.data.network.api

import com.example.pokemonapp.data.network.response.DetailResponse
import com.example.pokemonapp.data.network.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemon(
        @Query("limit") limit : Int?,
        @Query("offset") offset : Int?
    ) : PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getDetailPokemon(
        @Path("name") name : String
    ) : DetailResponse

}