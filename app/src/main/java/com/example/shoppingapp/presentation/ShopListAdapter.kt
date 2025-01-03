package com.example.shoppingapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var shopList = listOf<ShopItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop_disabled, parent, false)
        return ShopListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val item = shopList[position]
        holder.tvName.text = "${item.name} ${item.enabled}"
        holder.tvCount.text = item.count.toString()
        holder.itemView.setOnLongClickListener {
            true
        }

        if (item.enabled) {
            holder.tvName.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    android.R.color.holo_red_light
                )
            )
        }
    }

    override fun onViewRecycled(holder: ShopListViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
        holder.tvName.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                android.R.color.white
            )
        )
    }

    class ShopListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }

}

