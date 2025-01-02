package com.example.shoppingapp.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppingapp.data.ShopListRepositoryImpl
import com.example.shoppingapp.domain.AddShopItemUseCase
import com.example.shoppingapp.domain.EditShopItemUseCase
import com.example.shoppingapp.domain.GetShopItemUseCase
import com.example.shoppingapp.domain.GetShopListUseCase
import com.example.shoppingapp.domain.RemoveShopItemUseCase
import com.example.shoppingapp.domain.ShopItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {

    /*
        presentation-слой должен знать всё о domain слое
     */

    private val repository = ShopListRepositoryImpl()

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopListUseCase = RemoveShopItemUseCase(repository)

    //states
    private val _shopList = MutableStateFlow<List<ShopItem>>(mutableListOf())
    val shopList = _shopList.asStateFlow()

    fun getShopList() {
        val list = getShopListUseCase.getShopList()
        _shopList.value = list
    }

    fun removeShopItem(item: ShopItem) {
        removeShopListUseCase.removeShopItem(item)
        getShopList()
    }
    fun changeEnableState(item: ShopItem) {
        val newItem = item.copy(name = item.name, count = item.count, enabled = !item.enabled)
        editShopItemUseCase.editShopItem(newItem)
        getShopList()
    }
}