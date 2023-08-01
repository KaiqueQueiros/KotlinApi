package com.example.trevokotlin.model.orcamento

import com.google.gson.annotations.SerializedName

data class Orcamento(
    @SerializedName("idProposta")
    val idProposta: Int,
    @SerializedName("data")
    val data: List<Int>?,
    @SerializedName("cliente")
    val cliente: Cliente,
    @SerializedName("produtos")
    val produtos: List<Int>
)
