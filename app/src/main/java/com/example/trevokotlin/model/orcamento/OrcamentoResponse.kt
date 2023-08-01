package com.example.trevokotlin.model.orcamento

import com.google.gson.annotations.SerializedName

data class OrcamentoResponse(
    @SerializedName("content")
    val budgets: List<Orcamento>

)
