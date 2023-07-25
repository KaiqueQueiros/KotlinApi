package com.example.trevokotlin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.trevokotlin.api.Produto
import com.example.trevokotlin.api.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class DetailsProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_product)
        val productId = intent.getIntExtra("productId", 1)
        CoroutineScope(Dispatchers.Main).launch {
            getProductById(productId)
        }

        val addToCartButton = findViewById<Button>(R.id.buttonOrcamentoFromDetails)
        addToCartButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("lista_de_produtos", Context.MODE_PRIVATE)
            val productListString = sharedPreferences.getString("productList", "")
            val productList = productListString?.split(",")?.mapNotNull { it.toIntOrNull() }?.toMutableList() ?: mutableListOf()
            productList.add(productId)
            val updatedProductList = productList.joinToString(",")
            val editor = sharedPreferences.edit()
            editor.putString("productList", updatedProductList)
            editor.apply()

            Toast.makeText(this@DetailsProductActivity, "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show()
            println(productList)
        }

    }


    suspend fun getProductById(productId: Int) {
        withContext(Dispatchers.IO) {
            val retrofitClient: Call<Produto> =
                NetworkUtils().productService.getProductById(productId)
            val response: Response<Produto> = retrofitClient.execute()
            if (response.isSuccessful) {
                val product: Produto? = response.body()
                if (product != null) {
                    withContext(Dispatchers.Main) {
                        showProductDetails(product)
                    }
                } else {
                    Log.e("ProductDetailsActivity", "Empty response body.")
                }
            } else {
                Log.e(
                    "ProductDetailsActivity",
                    "Failed to get product details. Response code: ${response.code()}"
                )
            }
        }
    }

    fun showProductDetails(product: Produto) {
        val capaProduct = findViewById<ImageView>(R.id.imageCapa)
        val nomeProduct = findViewById<TextView>(R.id.nomeProductPreview)
        val productDescription = findViewById<TextView>(R.id.productDescription)
        val cultureDescription = findViewById<TextView>(R.id.cultureDescription)
        val areaDescription = findViewById<TextView>(R.id.areaDescription)
        nomeProduct.text = product.nome
        productDescription.text = product.descricao
        cultureDescription.text = product.culturas.toString()
        areaDescription.text = product.area
        Glide.with(this)
            .load("http://10.0.0.113:8080/trevo/api/produto/capa/${product.capa}")
            .into(capaProduct)

    }

    fun BackPage(view: View) {
        onBackPressed()
    }


}