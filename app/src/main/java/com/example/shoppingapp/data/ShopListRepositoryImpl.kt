package com.example.shoppingapp.data

import com.example.shoppingapp.domain.ShopItem
import com.example.shoppingapp.domain.ShopListRepository

class ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrement = 0

    init {
        for (i in 0 until 10) {
            addShopItem(ShopItem(name = "name $i", i, true))
        }
    }

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = autoIncrement++
        }
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