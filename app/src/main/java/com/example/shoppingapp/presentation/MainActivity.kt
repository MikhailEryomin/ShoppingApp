package com.example.shoppingapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
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
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupShopList()
        observingData()
    }

    private fun observingData() {
        lifecycleScope.launch {
            viewModel.shopList.collect {
                shopList = it
                adapter.submitList(it)
            }
        }
    }

    private fun setupShopList() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_shop_list)
        recyclerView.adapter = adapter
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )

        setOnShopItemHoldClickListener()
        setShopItemClickListener()
        setShopItemSwipeListener(recyclerView)
    }

    private fun setShopItemSwipeListener(recyclerView: RecyclerView?) {
        ShopItemSwipeCallBack.apply {
            viewModel = this@MainActivity.viewModel
            adapter = this@MainActivity.adapter
        }
        val itemTouchHelper = ItemTouchHelper(ShopItemSwipeCallBack.shopItemSwipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setOnShopItemHoldClickListener() {
        adapter.onShopItemHoldClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setShopItemClickListener() {
        adapter.onShopItemClickListener = {
            Log.d(
                "SHOPITEM_INFO",
                "Item: id: ${it.id}, name: ${it.name}, count: ${it.count}, enabled: ${it.enabled}"
            )
        }
    }


}