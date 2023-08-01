package com.example.trevokotlin.ui.orcamento

import android.content.Context
import android.content.Intent
import com.example.trevokotlin.adapter.CartAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trevokotlin.R
import com.example.trevokotlin.model.produto.Produto
import com.example.trevokotlin.config.NetworkUtils
import com.example.trevokotlin.ui.proposta.BudgetActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class CartActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productIds: List<Int>
    private lateinit var products: MutableList<Produto>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orcamento_actvity_main)
        recyclerView = findViewById(R.id.recycleViewBudget)
        products = mutableListOf()
        val sharedPreferences = getSharedPreferences("lista_de_produtos", Context.MODE_PRIVATE)
        val productListIds = sharedPreferences.getString("productList", "")
        productIds = productListIds?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
        getProductDetails()
        val buttonOrcamento = findViewById<AppCompatButton>(R.id.enviarSolicitacao)
        buttonOrcamento.setOnClickListener {
            val intent = Intent(this, BudgetActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getProductDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            val productList = mutableListOf<Produto>()
            for (productId in productIds) {
                try {
                    val retrofitClient: Call<Produto> = NetworkUtils().productService.getProductById(productId)
                    val response: Response<Produto> = retrofitClient.execute()
                    if (response.isSuccessful) {
                        val produto: Produto? = response.body()
                        if (produto != null) {
                            productList.add(produto)
                            println(produto)
                        }
                        println("Requisição bem-sucedida")
                    } else {
                        println("Resposta inválida: ${response.code()}")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            products.addAll(productList)
            withContext(Dispatchers.Main) {
                val adapter = CartAdapter(products)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@CartActivity)
            }
        }
    }

    fun BackPage(view: View) {
        onBackPressed()
    }
}

