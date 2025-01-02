package com.example.shoppingapp.data

import com.example.shoppingapp.domain.ShopItem
import com.example.shoppingapp.domain.ShopListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShopListRepositoryImpl: ShopListRepository {

    private val shopList = MutableStateFlow<List<ShopItem>>(mutableListOf())

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
        val updatedList = shopList.value.toMutableList()
        updatedList.add(item)
        shopList.value = updatedList
    }

    override fun editShopItem(item: ShopItem) {
        val oldItem = getShopItem(item.id) ?: throw IllegalArgumentException("Invalid item")
        removeShopItem(oldItem)
        addShopItem(item)
    }

    override fun removeShopItem(item: ShopItem) {
        val updatedList = shopList.value.toMutableList()
        updatedList.remove(item)
        shopList.value = updatedList
    }

    override fun getShopItem(itemID: Int): ShopItem? =
    shopList.value.find { it.id == itemID }

    override fun getShopList(): StateFlow<List<ShopItem>> {
        return shopList
    }
}