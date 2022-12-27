package com.example.pokemonapp.presentation.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.data.network.response.StatsItem
import com.example.pokemonapp.data.network.response.TypesItem
import com.example.pokemonapp.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object{
        fun getIntent(context : Context, name : String) = Intent(context, DetailActivity::class.java).putExtra("name", name)
    }

    private lateinit var binding : ActivityDetailBinding
    private val viewModel : DetailViewModel by viewModels()
    private lateinit var adapter : StatsAdapter
    private lateinit var typeAdapter : TypeAdapter
    private var name : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = StatsAdapter()
        typeAdapter = TypeAdapter()
        name = intent.getStringExtra("name").toString()
        viewModel.getDetailPokemon(name)
        showDetail()
    }

    private fun showDetail() {
        viewModel.loading.observe(this) {
            when(it){
                "loading" -> binding.progressCircular.isVisible = true
                "success" -> binding.progressCircular.isVisible = false
            }
        }

        viewModel.pokemon.observe(this) { pokemon ->
            binding.apply {
                tvName.text = "Detail ${pokemon.name}"
                tvNumber.text = "#0${pokemon.id}"
                tvPokemon.text = pokemon.name
                ivBack.setOnClickListener { onBackPressed() }
                tvWeight.text = "${pokemon.weight} cm"
                tvHeight.text = "${pokemon.height} kg"

                Glide.with(this@DetailActivity)
                    .load(pokemon.sprites?.other?.home?.frontDefault)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(binding.ivPokemon)

                typeAdapter.listTypes = pokemon.types as MutableList<TypesItem>
                rvTypes.adapter = typeAdapter
                rvTypes.isNestedScrollingEnabled = false

                adapter.listStat = pokemon.stats as MutableList<StatsItem>
                rvStats.adapter = adapter
            }
        }

    }
}