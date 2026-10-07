package com.example.praktikkummobile.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikkummobile.data.model.PokemonDetail
import com.example.praktikkummobile.data.model.PokemonResult
import com.example.praktikkummobile.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class PokemonUiState {
    object Loading : PokemonUiState()
    data class Success(val pokemonList: List<PokemonResult>) : PokemonUiState()
    data class Error(val message: String) : PokemonUiState()
}

sealed class PokemonDetailUiState {
    object Loading : PokemonDetailUiState()
    data class Success(val pokemon: PokemonDetail) : PokemonDetailUiState()
    data class Error(val message: String) : PokemonDetailUiState()
}

class PokemonViewModel : ViewModel() {
    private val repository = PokemonRepository()

    private val _uiState = MutableStateFlow<PokemonUiState>(PokemonUiState.Loading)
    val uiState: StateFlow<PokemonUiState> = _uiState

    private val _detailUiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val detailUiState: StateFlow<PokemonDetailUiState> = _detailUiState
    
    private var originalList = listOf<PokemonResult>()

    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModelScope.launch {
            _uiState.value = PokemonUiState.Loading
            try {
                val response = repository.getPokemonList()
                originalList = response.results
                _uiState.value = PokemonUiState.Success(originalList)
            } catch (e: Exception) {
                _uiState.value = PokemonUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun searchPokemon(query: String) {
        if (query.isBlank()) {
            _uiState.value = PokemonUiState.Success(originalList)
        } else {
            val filtered = originalList.filter { 
                it.name.contains(query, ignoreCase = true) 
            }
            _uiState.value = PokemonUiState.Success(filtered)
        }
    }

    fun getPokemonDetail(id: Int) {
        viewModelScope.launch {
            _detailUiState.value = PokemonDetailUiState.Loading
            try {
                val pokemon = repository.getPokemonDetail(id)
                _detailUiState.value = PokemonDetailUiState.Success(pokemon)
            } catch (e: Exception) {
                _detailUiState.value = PokemonDetailUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
