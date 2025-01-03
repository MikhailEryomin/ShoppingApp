package com.example.shoppingapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.ShopItem
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var shopList = listOf<ShopItem>()

    private val adapter = ShopListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupShopList()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observingData()
    }

    private fun observingData() {
        lifecycleScope.launch {
            viewModel.shopList.collect {
                shopList = it
                adapter.shopList = shopList
            }
        }
    }

    private fun setupShopList() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_shop_list)
        recyclerView.adapter = adapter
    }

}