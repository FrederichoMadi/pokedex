package com.example.pokemonapp.presentation.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.data.network.response.ResultsItem
import com.example.pokemonapp.databinding.ItemPokemonBinding
import com.example.pokemonapp.presentation.detail.DetailActivity
import com.example.pokemonapp.util.Helper.extractId
import com.example.pokemonapp.util.Helper.getPicUrl
import java.util.*

class PokemonAdapter : Adapter<PokemonAdapter.PokemonViewHolder>() {
    class PokemonViewHolder(itemView: View) : ViewHolder(itemView){
        private val binding = ItemPokemonBinding.bind(itemView)

        fun bind(pokemon : ResultsItem){
            binding.apply {

                val id = pokemon.url?.extractId()

                tvName.text = pokemon.name
                tvNumber.text = "#0$id"

                var image = pokemon.url?.getPicUrl()

                Glide.with(itemView.context)
                    .load(image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(binding.ivPokemon)

                itemView.setOnClickListener {
                    itemView.context.startActivity(DetailActivity.getIntent(itemView.context, pokemon.name.toString()))
                }
            }
        }
    }

    var listPokemon : MutableList<ResultsItem> = mutableListOf()


    fun filterList(list : MutableList<ResultsItem>){
        listPokemon = list
        notifyDataSetChanged()
    }

    fun updateList(pokemonList: MutableList<ResultsItem>, oldCount: Int, pokemonSize: Int) {
        listPokemon = pokemonList
        notifyItemRangeInserted(oldCount, pokemonSize)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(listPokemon[position])
    }

    override fun getItemCount(): Int = listPokemon.size
}