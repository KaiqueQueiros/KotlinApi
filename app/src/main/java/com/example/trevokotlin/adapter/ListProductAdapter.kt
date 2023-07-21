package com.example.trevokotlin.adapter

import android.content.Context
import android.content.Intent
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
import com.example.trevokotlin.api.Produto
import com.example.trevokotlin.DetailsProductActivity
import com.example.trevokotlin.api.ItemClickListener

class ListProductAdapter(private val context: Context, private val products: List<Produto>) :
    RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {

    private var itemClickListener: ItemClickListener? = null
    fun setOnItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun loadProducts(product: Produto) {
            val nameProduct = itemView.findViewById<TextView>(R.id.nomeProductPreview);
            val img = itemView.findViewById<ImageView>(R.id.imageProductCart)
            val maisVendido = itemView.findViewById<TextView>(R.id.itemMaisVendido)
            val verDetalhesButton = itemView.findViewById<Button>(R.id.detailsProductButton)
            verDetalhesButton.setOnClickListener {
                val productId = product.idProduto
                val intent = Intent(itemView.context, DetailsProductActivity::class.java)
                intent.putExtra("productId", productId)
                itemView.context.startActivity(intent)
            }
            if (product.maisVendido) {
                maisVendido.visibility = View.VISIBLE
            } else {
                maisVendido.visibility = View.GONE
            }
            nameProduct.text = product.nome
            try {
                Glide.with(itemView.context)
                    .load("http://10.0.0.113:8080/trevo/api/produto/foto/" + product.imagem)
                    .into(img)
            } catch (e: GlideException) {
                Log.d("TAG", e.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.preview_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.loadProducts(product)
    }

    override fun getItemCount(): Int = products.size
}