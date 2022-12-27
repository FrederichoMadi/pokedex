package com.example.pokemonapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.PokemonRepository
import com.example.pokemonapp.data.Resource
import com.example.pokemonapp.data.network.response.DetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository : PokemonRepository) : ViewModel() {

    private var _Loading = MutableLiveData<String>()
    val loading : LiveData<String> = _Loading

    private var _pokemon = MutableLiveData<DetailResponse>()
    var pokemon : LiveData<DetailResponse> = _pokemon

    fun getDetailPokemon(name : String) {
        viewModelScope.launch {
            repository.getDetailPokemon(name).collect{
                when(it){
                    is Resource.Loading -> {
                        _Loading.postValue("loading")
                    }
                    is Resource.Success -> {
                        _Loading.postValue("success")
                        _pokemon.postValue(it.data!!)
                    }
                    is Resource.Empty -> {
                        _Loading.postValue("Data is empty")
                    }
                }
            }
        }
    }

}