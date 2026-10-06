package com.example.praktikkummobile.data.repository

import com.example.praktikkummobile.data.model.PokemonDetail
import com.example.praktikkummobile.data.model.PokemonListResponse
import com.example.praktikkummobile.data.remote.ApiClient

class PokemonRepository {
    private val apiService = ApiClient.instance

    suspend fun getPokemonList(): PokemonListResponse {
        return apiService.getPokemonList()
    }

    suspend fun getPokemonDetail(id: Int): PokemonDetail {
        return apiService.getPokemonDetail(id)
    }
}
