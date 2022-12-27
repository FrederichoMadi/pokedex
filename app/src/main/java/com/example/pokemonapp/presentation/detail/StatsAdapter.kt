package com.example.pokemonapp.presentation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pokemonapp.R
import com.example.pokemonapp.data.network.response.Stat
import com.example.pokemonapp.data.network.response.StatsItem
import com.example.pokemonapp.databinding.ItemPokemonBinding
import com.example.pokemonapp.databinding.ItemStatsBinding

class StatsAdapter : Adapter<StatsAdapter.StatsViewHolder>() {
    class StatsViewHolder(itemView: View) : ViewHolder(itemView){
        private val binding = ItemStatsBinding.bind(itemView)
        fun bind(stat : StatsItem){
            binding.apply {
                tvStatsName.text = stat.stat?.name
                tvStats.text = stat.baseStat.toString()
                progressCircular.progress = stat.baseStat!!
            }
        }
    }

    var listStat : MutableList<StatsItem> = mutableListOf()
    set(value) {
        if(value.isEmpty()) return
        field.clear()
        field.addAll(value)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stats, parent, false)
        return StatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        holder.bind(listStat[position])
    }

    override fun getItemCount(): Int = listStat.size
}