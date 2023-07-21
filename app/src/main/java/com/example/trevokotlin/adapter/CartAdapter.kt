package com.example.trevokotlin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.example.trevokotlin.R
import com.example.trevokotlin.api.ItemClickListener
import com.example.trevokotlin.api.Produto

class CartAdapter(private val products: List<Produto>)
    : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var productList: List<Produto> = emptyList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(product: Produto) {
            val nameProduct = itemView.findViewById<TextView>(R.id.nomeProdutoCart)
            val img = itemView.findViewById<ImageView>(R.id.imageProductCart)
            val lixeira = itemView.findViewById<Button>(R.id.detailsProductButton)
            nameProduct.text = product.nome
            try {
                Glide.with(itemView.context)
                    .load("http://10.0.0.113:8080/trevo/api/produto/foto/${product.imagem}")
                    .into(img)
            } catch (e: GlideException) {
                Log.d("TAG", e.toString())
            }
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

    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(products: List<Produto>) {
        productList = products
        notifyDataSetChanged()
        println("Products list: $productList")
    }
}
