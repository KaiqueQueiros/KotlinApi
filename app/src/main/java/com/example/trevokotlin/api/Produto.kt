package com.example.trevokotlin.api

import java.io.Serializable

data class Produto(
    val idProduto: Int,
    val nome: String,
    val descricao: String,
    val area: String,
    val imagem: String,
    val logo: String,
    val capa: String,
    val maisVendido : Boolean,
    val culturas: List<String>
) : Serializable
