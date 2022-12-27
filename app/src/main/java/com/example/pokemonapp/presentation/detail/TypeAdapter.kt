package com.example.pokemonapp.presentation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.data.network.response.StatsItem
import com.example.pokemonapp.data.network.response.TypesItem
import com.example.pokemonapp.databinding.ItemStatsBinding
import com.example.pokemonapp.databinding.ItemTypeBinding

class TypeAdapter : RecyclerView.Adapter<TypeAdapter.TypeViewHolder>() {
    class TypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemTypeBinding.bind(itemView)
        fun bind(type : TypesItem){
            binding.apply {
                tvName.text = type.type?.name

            }
        }
    }

    var listTypes : MutableList<TypesItem> = mutableListOf()
        set(value) {
            if(value.isEmpty()) return
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_type, parent, false)
        return TypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        holder.bind(listTypes[position])
    }

    override fun getItemCount(): Int = listTypes.size
}