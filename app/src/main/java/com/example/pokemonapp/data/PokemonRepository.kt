package com.example.pokemonapp.data

import com.example.pokemonapp.data.network.RemoteDataSource
import com.example.pokemonapp.data.network.api.ApiResponse
import com.example.pokemonapp.data.network.response.DetailResponse
import com.example.pokemonapp.data.network.response.ResultsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getPokemon(limit : Int, offset : Int) : Flow<Resource<List<ResultsItem>>> {
        return flow{
            val apiResponse = remoteDataSource.getPokemon(limit, offset).first()
            emit(Resource.Loading())
            when(apiResponse){
                is ApiResponse.Success -> {
                    emit(Resource.Success(apiResponse.data?.results))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Empty(apiResponse.message))

                }
                else -> throw java.lang.IllegalArgumentException("Unknown response from get pokemons...")
            }
        }
    }

    suspend fun getDetailPokemon(name : String) : Flow<Resource<DetailResponse>> {
        return flow{
            val response = remoteDataSource.getDetailPokemon(name).first()
            emit(Resource.Loading())
            when(response) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(response.data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Empty(response.message))
                }
                else -> throw java.lang.IllegalArgumentException("Unknown response from get pokemon...")
            }
        }
    }
}