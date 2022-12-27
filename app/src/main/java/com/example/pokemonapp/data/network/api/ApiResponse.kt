package com.example.pokemonapp.data.network.api

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T? = null) : ApiResponse<T>()
    data class Error(val message : String? = null) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}