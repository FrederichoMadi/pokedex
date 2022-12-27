package com.example.pokemonapp.data.local.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import java.sql.SQLException

class PokemonHelper(context : Context) {

    private var dataBaseHelper : DatabaseHelper = DatabaseHelper(context)
    private lateinit var database : SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = "pokemon"

        private var INSTANCE : PokemonHelper? = null
        fun getInstance(context : Context) : PokemonHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: PokemonHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }



}