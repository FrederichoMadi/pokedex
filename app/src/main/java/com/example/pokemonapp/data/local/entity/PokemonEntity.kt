package com.example.pokemonapp.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonEntity(
    var name : String? = null,
    var url : String? = null
) : Parcelable