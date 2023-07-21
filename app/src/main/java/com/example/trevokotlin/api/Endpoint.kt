package com.example.trevokotlin.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {
    @GET(value = "api/produtos")
    fun getProducts(): Call<ProductResponse>

    @GET(value = "api/produto/{id}")
    fun getProductById(@Path(value = "id",encoded = true) id : Int) : Call<Produto>

    @GET(value = "api/propostas")
    fun getBudgetByEmail(@Query("email") email: String): Call<BudgetResponse>
    @POST(value = "api/proposta")
    fun enviaSolicitacao(@Body orcamento: JsonObject) : Call<JsonObject>


}