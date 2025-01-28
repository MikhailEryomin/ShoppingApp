package com.example.shoppingapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityMainBinding
import com.example.shoppingapp.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel
    private var shopList = listOf<ShopItem>()

    private val adapter = ShopListAdapter()
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        shopItemContainer = binding.shopItemContainer
        setupShopList()
        observingData()
        setupAddButton()
    }

    private fun isLandscapeOrientation(): Boolean {
        return binding.shopItemContainer != null
    }

    private fun setupAddButton() {
        val addButton = binding.buttonAddShopItem
        addButton.setOnClickListener {
            if (isLandscapeOrientation()) {
                launchFragment(ShopItemFragment.newInstanceAdd())
            } else {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
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
        val recyclerView = binding.rvShopList
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
            if (isLandscapeOrientation()) {
                launchFragment(ShopItemFragment.newInstanceEdit(item.id))
            } else {
                val intent = ShopItemActivity.newIntentEditItem(this, item.id)
                startActivity(intent)
            }
        }
    }


}