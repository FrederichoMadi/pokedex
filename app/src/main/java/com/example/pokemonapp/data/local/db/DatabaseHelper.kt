package com.example.pokemonapp.data.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


class DatabaseHelper(context : Context) : SQLiteOpenHelper(context, "pokemon.db", null, 1) {

    companion object {
        private const val SQL_CREATE_POKEMON = """
            CREATE TABLE ${DatabaseContract.PokemonEntry.TABLE_NAME} (
            ${BaseColumns._ID} INTEGER PRIMARY KEY,
            ${DatabaseContract.PokemonEntry.COLUMN_NAME_TITLE} TEXT,
            ${DatabaseContract.PokemonEntry.COLUMN_NAME_URL} TEXT)
        """

        private const val SQL_DELETE_POKEMON = "DROP TABLE IF EXISTS ${DatabaseContract.PokemonEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_POKEMON)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SQL_DELETE_POKEMON)
        onCreate(db)
    }


}