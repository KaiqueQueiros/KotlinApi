package com.example.trevokotlin

import com.example.trevokotlin.adapter.OrcamentoAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trevokotlin.api.Orcamento
import com.example.trevokotlin.api.Produto
import com.example.trevokotlin.api.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class BudgetConsult : AppCompatActivity() {
    private lateinit var products: List<Produto>
    private lateinit var emailEditText: EditText
    private lateinit var searchButton: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.consulta_orcamento)
        emailEditText = findViewById(R.id.email)
        searchButton = findViewById(R.id.search)
        searchButton.setOnClickListener {
            val email: String = emailEditText.text.toString().trim()
            if (email.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    searchBudgetByEmail(email)
                }
            } else {
                Toast.makeText(this, "Por favor, insira um email.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun BackPage(view: View) {
        onPause()
        onBackPressed()
    }

    private fun searchBudgetByEmail(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = NetworkUtils().productService.getBudgetByEmail(email).execute()
                val budgetResponse = response.body()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && budgetResponse != null) {
                        val budgets = budgetResponse.budgets
                        processBudget(budgets)
                    } else {
                        Log.e(
                            "MainActivity",
                            "Failed to get budgets. Response code: ${response.code()}"
                        )
                    }
                }
            } catch (e: IOException) {
                Log.e("MainActivity", "Error: ${e.message}")
            }
        }
    }

    private fun processBudget(budgets: List<Orcamento>) {
        val orcamentosRecyclerView = findViewById<RecyclerView>(R.id.recycleViewOrcamento)
        orcamentosRecyclerView.layoutManager = LinearLayoutManager(this)
        val orcamentosAdapter = OrcamentoAdapter(this@BudgetConsult, budgets)
        orcamentosRecyclerView.adapter = orcamentosAdapter
    }
}
