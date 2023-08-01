package com.example.trevokotlin.config

import com.example.trevokotlin.model.orcamento.OrcamentoResponse
import com.example.trevokotlin.model.produto.ProdutoResponse
import com.example.trevokotlin.model.produto.Produto
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EndpointsApi {
    @GET(value = "api/produtos")
    fun getProducts(): Call<ProdutoResponse>

    @GET(value = "api/produto/{id}")
    fun getProductById(@Path(value = "id",encoded = true) id : Int) : Call<Produto>

    @GET(value = "api/propostas")
    fun getBudgetByEmail(@Query("email") email: String): Call<OrcamentoResponse>
    @POST(value = "api/proposta")
    fun enviaSolicitacao(@Body orcamento: JsonObject) : Call<JsonObject>


}