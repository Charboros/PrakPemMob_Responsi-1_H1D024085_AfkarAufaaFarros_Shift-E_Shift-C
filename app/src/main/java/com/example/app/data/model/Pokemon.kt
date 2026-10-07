package com.example.praktikkummobile.data.model

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
) {
    val id: Int
        get() = url.split("/").dropLast(1).last().toInt()
        
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}
