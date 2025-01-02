package com.example.shoppingapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.ShopItem
import kotlinx.coroutines.launch

class MainActivity: AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private var count = 0

    private var shopList = listOf<ShopItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        //Observing
        observingData()
    }

    private fun observingData() {
        lifecycleScope.launch {
            viewModel.shopList.collect {
                shopList = it
                Log.d("OBSERVE", it.toString())
                if (count == 0) {
                    count++
                    val item = it[0]
                    viewModel.changeEnableState(item)
                }
            }
        }
    }

}