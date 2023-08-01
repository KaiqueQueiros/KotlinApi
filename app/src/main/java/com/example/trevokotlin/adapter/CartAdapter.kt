package com.example.trevokotlin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.example.trevokotlin.R
import com.example.trevokotlin.model.produto.Produto
import androidx.appcompat.app.AlertDialog


class CartAdapter(private val products: List<Produto>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(product: Produto) {
            val nameProduct = itemView.findViewById<TextView>(R.id.nomeProdutoCart)
            val img = itemView.findViewById<ImageView>(R.id.imageCarrinho)
            val lixeira = itemView.findViewById<ImageButton>(R.id.lixeira)

            lixeira.setOnClickListener {
                showDeleteConfirmationDialog(product)
            }

            nameProduct.text = product.nome
            try {
                Glide.with(itemView.context)
                    .load("http://10.0.0.113:8080/trevo/api/produto/foto/${product.imagem}")
                    .into(img)
            } catch (e: GlideException) {
                Log.d("TAG", e.toString())
            }
        }

        private fun showDeleteConfirmationDialog(product: Produto) {
            val alertDialog = AlertDialog.Builder(itemView.context)
                .setTitle("Excluir Produto")
                .setMessage("Deseja realmente excluir o produto ${product.nome}?")
                .setPositiveButton("Sim") { dialog, _ ->
                    removeProduct(product)
                    dialog.dismiss()
                }
                .setNegativeButton("Não") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            alertDialog.show()
        }


        @SuppressLint("NotifyDataSetChanged")
        private fun removeProduct(product: Produto) {
            val sharedPreferences = itemView.context.getSharedPreferences("lista_de_produtos", Context.MODE_PRIVATE)
            val productListString = sharedPreferences.getString("productList", "")
            val productList = productListString?.split(",")?.mapNotNull { it.toIntOrNull() }?.toMutableList() ?: mutableListOf()
            productList.remove(product.idProduto)
            val editor = sharedPreferences.edit()
            val updatedProductListString = productList.joinToString(",")
            editor.putString("productList", updatedProductListString)
            editor.apply()
            products.toMutableList().remove(product)
            notifyDataSetChanged()
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.details_carrinho, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

}

