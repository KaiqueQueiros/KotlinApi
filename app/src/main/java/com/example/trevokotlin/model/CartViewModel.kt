package com.example.trevokotlin.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    private val _cartItems: MutableLiveData<List<Int>> = MutableLiveData()
    val cartItems: LiveData<List<Int>> get() = _cartItems

    fun addToCart(productId: Int) {
        val currentItems = _cartItems.value.orEmpty().toMutableList()
        currentItems.add(productId)
        _cartItems.value = currentItems
        println(cartItems.value)
    }
}
