package com.example.shoppingapp.data

import com.example.shoppingapp.domain.ShopItem
import com.example.shoppingapp.domain.ShopListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShopListRepositoryImpl: ShopListRepository {

    private val shopListFlow = MutableStateFlow<List<ShopItem>>(listOf())
    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrement = 0

    init {
        for (i in 0 until 16) {
            addShopItem(ShopItem(name = "name $i", i, true))
        }
    }

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = autoIncrement++
        }
        shopList.add(item)
        updateListFlow()
    }

    override fun editShopItem(item: ShopItem) {
        val oldItem = getShopItem(item.id) ?: throw IllegalArgumentException("Invalid item")
        removeShopItem(oldItem)
        addShopItem(item)
    }

    override fun removeShopItem(item: ShopItem) {
        shopList.remove(item)
        updateListFlow()
    }

    override fun getShopItem(itemID: Int): ShopItem? =
    shopList.find { it.id == itemID }

    override fun getShopList(): StateFlow<List<ShopItem>> {
        return shopListFlow
    }

    private fun updateListFlow() {
        shopListFlow.value = shopList.toList()
    }
}