package com.example.pokemonapp.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.Q

object Helper {

    val Context.isNetworkConnected: Boolean
        get() {
            val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (SDK_INT >= Q)
                manager.getNetworkCapabilities(manager.activeNetwork)?.let {
                    it.hasTransport(TRANSPORT_WIFI) || it.hasTransport(TRANSPORT_CELLULAR) ||
                        it.hasTransport(TRANSPORT_BLUETOOTH) ||
                        it.hasTransport(TRANSPORT_ETHERNET) ||
                        it.hasTransport(TRANSPORT_VPN)
                } ?: false
            else
                @Suppress("DEPRECATION")
                manager.activeNetworkInfo?.isConnected == true
        }

    fun String.extractId() = this.substringAfter("pokemon").replace("/", "").toInt()

    fun String.getPicUrl() : String {
        val id = this.extractId()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
    }
}