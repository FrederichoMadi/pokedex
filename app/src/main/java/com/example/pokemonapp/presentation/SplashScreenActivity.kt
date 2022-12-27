package com.example.pokemonapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.pokemonapp.databinding.ActivitySplashScreenBinding
import com.example.pokemonapp.presentation.home.MainActivity
import com.example.pokemonapp.util.Helper.isNetworkConnected

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkConnection()
    }

    private fun checkConnection() {
        if(this.isNetworkConnected){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }, 2000)

        } else {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show()
        }
    }
}