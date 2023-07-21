package com.example.trevokotlin.api

import com.google.gson.JsonObject

data class DadosOrcamento(
    val cliente: JsonObject,
    val produtos : List<Int>
)
