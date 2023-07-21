package com.example.trevokotlin.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkUtils {
    var getRetrofitInstance = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://10.0.0.113:8080/trevo/")
        .build()
    val productService = getRetrofitInstance.create(Endpoint::class.java)

}