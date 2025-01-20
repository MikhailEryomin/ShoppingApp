package com.example.shoppingapp.presentation

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

object ShopItemSwipeCallBack {

    var viewModel: MainViewModel? = null
    var adapter: ShopListAdapter? = null

    val shopItemSwipeCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            //Toast.makeText(this@MainActivity, "on Move", Toast.LENGTH_SHORT).show()
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            //Toast.makeText(this@MainActivity, "on Swiped ", Toast.LENGTH_SHORT).show()
            //Remove swiped item from list and notify the RecyclerView
            val position = viewHolder.adapterPosition
            if (adapter != null) {
                viewModel?.removeShopItem(adapter!!.shopList[position])
            }
        }
    }

}