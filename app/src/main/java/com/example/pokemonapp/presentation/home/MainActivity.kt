package com.example.pokemonapp.presentation.home

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.pokemonapp.R
import com.example.pokemonapp.data.local.db.DatabaseContract
import com.example.pokemonapp.data.local.db.PokemonHelper
import com.example.pokemonapp.data.network.response.ResultsItem
import com.example.pokemonapp.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var adapter : PokemonAdapter
    private var isLoading = false
    private var pokemonList = mutableListOf<ResultsItem>()
    private var limit = 0
    private var offset = 0
    private var isSearching = false
    private lateinit var pokemonHelper : PokemonHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PokemonAdapter()
        pokemonHelper = PokemonHelper.getInstance(this)
        pokemonHelper.open()

        showData()
        searchPokemon()
        sortItem()
        initScrollListener()
    }

    private fun initScrollListener(){
        isLoading = true
        binding.rvItem.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isLoadMore()
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                if(isLoading && !isSearching){
                        if(!binding.rvItem.canScrollVertically(1)){

                                isLoading = false
                                limit += 20
                                offset += 20
                                viewModel.getPokemon(limit, offset)
                                adapter.notifyItemRangeInserted(adapter.itemCount, pokemonList.size)
                                //fetchData
                                isLoading = true

                        }
                    }



            }
        })
    }

    private fun isLoadMore(){
        binding.loadMore.isVisible = isLoading
    }

    private fun sortItem() {
        binding.fabFilter.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_filter, null)

            var cbAZ = dialogView.findViewById<RadioButton>(R.id.cbAZ)
            var cbZA = dialogView.findViewById<RadioButton>(R.id.cbZA)

            var bottomSheetDialog = BottomSheetDialog(this@MainActivity)
            bottomSheetDialog.setContentView(dialogView)
            bottomSheetDialog.show()

            cbAZ.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    adapter.listPokemon.sortBy { it.name }
                    adapter.notifyDataSetChanged()
                    bottomSheetDialog.dismiss()
                }
            }

            cbZA.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    adapter.listPokemon.sortByDescending { it.name }
                    adapter.notifyDataSetChanged()
                    bottomSheetDialog.dismiss()
                }
            }
        }
    }

    fun filter(text : String) {
        val filteredList = mutableListOf<ResultsItem>()

        for(item in adapter.listPokemon){
            if(item.name?.lowercase()!!.contains(text.lowercase())){
                filteredList.add(item)
            }
        }

        Log.d("MainActivity", "size : ${filteredList.size}")

        if(filteredList.isEmpty()){
            Toast.makeText(this, "no data found..", Toast.LENGTH_SHORT).show()
        } else {
            adapter.filterList(filteredList)
        }
    }

    private fun searchPokemon(){
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(text!!.isEmpty()){
                    showData()
                    isSearching = false
                } else {
                    filter(text.toString())
                    isSearching = true
                }
            }

            override fun afterTextChanged(text: Editable?) {
                if(text!!.toString().isEmpty()){

                }

            }

        })
    }

    private fun showData() {
        viewModel.isLoading.observe(this) {
            when(it){
                "loading" -> {
                    binding.progressCircular.isVisible = true
                }
                "success" -> {
                    binding.progressCircular.isVisible = false
                }
            }
        }

        viewModel.pokemons.observe(this) {
            var oldCount = it.size
            pokemonList.addAll(it)
            adapter.updateList(pokemonList, oldCount, pokemonList.size)
            binding.rvItem.adapter = adapter

            var result = 0L
            val values = ContentValues()
            for (item in pokemonList){
                values.put(DatabaseContract.PokemonEntry.COLUMN_NAME_TITLE, item.name)
                values.put(DatabaseContract.PokemonEntry.COLUMN_NAME_URL, item.url)
                result = pokemonHelper.insert(values)
            }

            Log.d("MainACtivity", "Size insert : ${result}")


        }

    }
}