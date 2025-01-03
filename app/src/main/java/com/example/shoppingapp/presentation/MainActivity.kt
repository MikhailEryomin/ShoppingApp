package com.example.shoppingapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.ShopItem
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llShopList: LinearLayout

    private var count = 0

    private var shopList = listOf<ShopItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        llShopList = findViewById(R.id.shop_list_linear)
        //Observing
        observingData()
    }

    private fun observingData() {
        lifecycleScope.launch {
            viewModel.shopList.collect {
                shopList = it
                showList(shopList)
            }
        }
    }

    private fun showList(list: List<ShopItem>) {
        llShopList.removeAllViews()
        list.forEach { item ->
            val layoutId =
                if (item.enabled) R.layout.item_shop_enabled else R.layout.item_shop_disabled

            val view = LayoutInflater.from(this).inflate(layoutId, llShopList, false)

            val itemName = view.findViewById<TextView>(R.id.tv_name)
            val itemCount = view.findViewById<TextView>(R.id.tv_count)

            itemName.text = item.name
            itemCount.text = item.count.toString()

            view.setOnLongClickListener {
                viewModel.changeEnableState(item)
                true
            }

            llShopList.addView(view)
        }
    }

}