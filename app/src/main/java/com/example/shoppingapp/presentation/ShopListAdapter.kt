package com.example.shoppingapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ItemShopDisabledBinding
import com.example.shoppingapp.databinding.ItemShopEnabledBinding
import com.example.shoppingapp.domain.ShopItem


class ShopListAdapter : ListAdapter<ShopItem, ShopListAdapter.ShopListViewHolder>(ShopItemDiffCallBack()) {

    var onShopItemHoldClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout =
            if (viewType == VIEW_TYPE_DISABLED)
                R.layout.item_shop_disabled
            else
                R.layout.item_shop_enabled
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding

        holder.itemView.setOnLongClickListener {
            onShopItemHoldClickListener?.invoke(item)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(item)
        }

        when (binding) {
            is ItemShopEnabledBinding -> {
                binding.tvName.text = "${item.name} ${item.enabled}"
                binding.tvCount.text = item.count.toString()
            }
            is ItemShopDisabledBinding -> {
                binding.tvName.text = "${item.name} ${item.enabled}"
                binding.tvCount.text = item.count.toString()
            }
        }
    }



    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    override fun onViewRecycled(holder: ShopListViewHolder) {
        super.onViewRecycled(holder)
        when (val binding = holder.binding) {
            is ItemShopEnabledBinding -> {
                binding.tvName.text = ""
                binding.tvCount.text = ""
            }
            is ItemShopDisabledBinding -> {
                binding.tvName.text = ""
                binding.tvCount.text = ""
            }
        }
    }

    class ShopListViewHolder(
        val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = -1

        const val MAX_POOL_SIZE = 15
    }

}

