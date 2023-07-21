package com.example.trevokotlin.api

import com.google.gson.annotations.SerializedName

data class BudgetResponse(
    @SerializedName("content")
    val budgets: List<Orcamento>

)
