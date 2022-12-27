package com.example.pokemonapp.data.local.db

import android.provider.BaseColumns

object DatabaseContract {
    object PokemonEntry : BaseColumns {
        const val TABLE_NAME = "pokemon"
        const val COLUMN_NAME_TITLE = "name"
        const val COLUMN_NAME_URL = "url"


    }
}