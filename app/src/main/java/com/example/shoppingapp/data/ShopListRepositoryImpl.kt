package com.example.shoppingapp.data

import com.example.shoppingapp.domain.ShopItem
import com.example.shoppingapp.domain.ShopListRepository

class ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    override fun addShopItem(item: ShopItem) {
        shopList.add(item)
    }

    override fun editShopItem(item: ShopItem) {
        val oldItem = getShopItem(item.id) ?: throw IllegalArgumentException("Invalid item")
        removeShopItem(oldItem)
        addShopItem(item)
    }

    override fun removeShopItem(item: ShopItem) {
        shopList.remove(item)
    }

    override fun getShopItem(itemID: Int): ShopItem? =
    shopList.find { it.id == itemID }

    override fun getShopList(): List<ShopItem> = shopList
}