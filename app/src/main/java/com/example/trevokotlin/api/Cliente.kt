package com.example.trevokotlin.api

import com.google.gson.annotations.SerializedName

data class Cliente(
    @SerializedName("nome")
    val nome: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("telefone")
    val telefone: String
)
