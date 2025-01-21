package com.example.shoppingapp.presentation.MainActivity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.ShopItem
import com.example.shoppingapp.presentation.ShopItemActivity.ShopItemActivity
import com.example.shoppingapp.presentation.ShopItemSwipeCallBack
import com.example.shoppingapp.presentation.ShopListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        setupAddButton()
    }

    private fun setupAddButton() {
        val addButton = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        addButton.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun observingData() {
        lifecycleScope.launch {
            viewModel.shopList.collect {
                shopList = it
                adapter.submitList(it)
                Log.d("ADD", adapter.currentList.toString())
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
        adapter.onShopItemClickListener = { item ->
            val intent = ShopItemActivity.newIntentEditItem(this, item.id)
            startActivity(intent)
        }
    }


}