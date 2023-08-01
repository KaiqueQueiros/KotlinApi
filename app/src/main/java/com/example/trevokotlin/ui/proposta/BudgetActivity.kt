package com.example.trevokotlin.ui.proposta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.trevokotlin.MainActivity
import com.example.trevokotlin.R
import com.example.trevokotlin.config.NetworkUtils
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BudgetActivity : AppCompatActivity() {
    private lateinit var nomeCliente: EditText
    private lateinit var emailCliente: EditText
    private lateinit var telefoneCliente: EditText
    private lateinit var enviaSolicitacao: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_formulario_cliente)
        val sharedPreferences = getSharedPreferences("lista_de_produtos", Context.MODE_PRIVATE)
        val productListString = sharedPreferences.getString("productList", "")
        val productIds = productListString?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
        enviaSolicitacao = findViewById(R.id.enviarSolicitacaoButton)
        enviaSolicitacao.setOnClickListener {
            nomeCliente = findViewById(R.id.nome)
            emailCliente = findViewById(R.id.email)
            telefoneCliente = findViewById(R.id.telefone)
            val nome = nomeCliente.text.toString()
            val email = emailCliente.text.toString()
            val telefone = telefoneCliente.text.toString()
            val jsonDadosOrcamento = JsonObject()
            val jsonCliente = JsonObject()
            jsonCliente.addProperty("nome", nome)
            jsonCliente.addProperty("email", email)
            jsonCliente.addProperty("telefone", telefone)
            jsonDadosOrcamento.add("cliente", jsonCliente)
            val jsonProdutos = JsonArray()
            for (produtoId in productIds) {
                jsonProdutos.add(produtoId)
            }
            jsonDadosOrcamento.add("produtos", jsonProdutos)
            val apiService = NetworkUtils().productService
            apiService.enviaSolicitacao(jsonDadosOrcamento).enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val editor = sharedPreferences.edit()
                        editor.remove("productList")
                        editor.apply()
                        val intent = Intent(this@BudgetActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this@BudgetActivity, "Solicitação enviada com sucesso ", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@BudgetActivity, "Erro na solicitação a api", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(this@BudgetActivity, "Erro na solicitação", Toast.LENGTH_SHORT).show()
                }
            })


        }

    }
}