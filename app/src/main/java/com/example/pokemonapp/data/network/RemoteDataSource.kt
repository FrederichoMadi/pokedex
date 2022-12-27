package com.example.pokemonapp.data.network

import android.util.Log
import com.example.pokemonapp.data.network.api.ApiResponse
import com.example.pokemonapp.data.network.api.ApiService
import com.example.pokemonapp.data.network.response.DetailResponse
import com.example.pokemonapp.data.network.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService){

    fun getPokemon(limit: Int, offset: Int) : Flow<ApiResponse<PokemonResponse>>{
        return flow {
            try {
                val response = apiService.getPokemon(limit, offset)
                if(response.results != null){
                    if(response.results.isNotEmpty()){
                        emit(ApiResponse.Success(response))
                    } else {
                        emit(ApiResponse.Empty)
                        Log.d("RemoteDataSource", "Pokemon result is empty")
                    }
                }
            } catch (e : java.lang.Exception){
                emit(ApiResponse.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)

    }

    fun getDetailPokemon(name : String) : Flow<ApiResponse<DetailResponse>>{
        return flow {
            try {
                val response = apiService.getDetailPokemon(name)
                if(response.id != 0){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : java.lang.Exception){
                emit(ApiResponse.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }
}