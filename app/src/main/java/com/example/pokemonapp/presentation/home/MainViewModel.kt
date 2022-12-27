package com.example.pokemonapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.PokemonRepository
import com.example.pokemonapp.data.Resource
import com.example.pokemonapp.data.network.response.ResultsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {

    private var _pokemons = MutableLiveData<List<ResultsItem>>()
    val pokemons : LiveData<List<ResultsItem>> = _pokemons

    private var _isLoading = MutableLiveData<String>()
    val isLoading get() = _isLoading

    init {
        getPokemon(0,0)
    }

    fun getPokemon(limit : Int, offset: Int){
        viewModelScope.launch {
            repository.getPokemon(limit, offset).collect{
                when(it){
                    is Resource.Loading -> {
                        _isLoading.postValue("loading")
                    }
                    is Resource.Success -> {
                        _isLoading.postValue("success")
                        _pokemons.postValue(it.data!!)
                    }
                    is Resource.Empty -> {
                        _isLoading.postValue("Pokemon is Empty")
                    }
                }
            }
        }
    }
}