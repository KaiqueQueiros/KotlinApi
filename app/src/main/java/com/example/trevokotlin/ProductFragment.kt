package com.example.trevokotlin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.trevokotlin.adapter.ProductAdapter
import com.example.trevokotlin.api.Produto
import com.example.trevokotlin.api.ItemClickListener
import com.example.trevokotlin.api.NetworkUtils
import com.example.trevokotlin.api.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.io.IOException


class ProductFragment : Fragment(), ItemClickListener {
    private lateinit var products: List<Produto>
    private lateinit var searchButton: ImageButton
    private lateinit var pesquisar: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_produto, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycleView)
        pesquisar = view.findViewById(R.id.pesquisar)
        searchButton = view.findViewById(R.id.searchProduct)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val retrofitClient: Call<ProductResponse> =
                    NetworkUtils().productService.getProducts()
                val response: Response<ProductResponse> = retrofitClient.execute()
                if (response.isSuccessful) {
                    val productResponse: ProductResponse? = response.body()
                    products = productResponse?.content ?: emptyList()
                    withContext(Dispatchers.Main) {
                        val adapter = ProductAdapter(requireContext(), products)
                        adapter.setOnItemClickListener(this@ProductFragment)
                        recyclerView.adapter = adapter
                    }

                    println("Requisição bem-sucedida")
                } else {
                    println("Resposta inválida: ${response.code()}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        searchButton.setOnClickListener {
            val inputText = pesquisar.text.toString().trim()
            if (inputText.isNotEmpty()) {
                val productId = inputText.toIntOrNull()
                if (productId != null) {
                    DetailsProduct(productId)
                } else {
                    Toast.makeText(context, "Por favor, insira um ID de produto válido.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Por favor, insira um ID de produto.", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun clickItem(productId: Int) {
        DetailsProduct(productId)
        println("Cliquei no produto $productId")
    }

    private fun DetailsProduct(productId: Int) {
        val intent = Intent(requireContext(), DetailsProductActivity::class.java)
        intent.putExtra("productId", productId)
        startActivity(intent)
    }
}
