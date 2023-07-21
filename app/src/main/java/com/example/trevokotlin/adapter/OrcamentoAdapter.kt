package com.example.trevokotlin.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trevokotlin.R
import com.example.trevokotlin.api.Orcamento


class OrcamentoAdapter(private val context: Context, private val orcamentos: List<Orcamento>) :
    RecyclerView.Adapter<OrcamentoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun loadBudget(budget: Orcamento) {
            val produtos = itemView.findViewById<Button>(R.id.products)
            val dataOrcamento = itemView.findViewById<Button>(R.id.dataPedido)
            val nomeCliente = itemView.findViewById<TextView>(R.id.nomeCliente)
            val emailCliente = itemView.findViewById<TextView>(R.id.emailDoClient)
            val produtosList = budget.produtos?.map { it.toInt() }
            val produtosInt = produtosList?.joinToString(", ")
            produtos.text = produtosInt
            nomeCliente.text = budget.cliente.nome
            emailCliente.text = budget.cliente.email
            val dataIntList = budget.data?.map { it.toInt() }
            val dataString = dataIntList?.joinToString(", ")
            dataOrcamento.text = dataString

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.preview_orcamento, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val budget = orcamentos[position]
        holder.loadBudget(budget)

    }



    override fun getItemCount(): Int {
        return orcamentos.size
    }
}
