package com.example.shoppingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.ShopListRepositoryImpl
import com.example.shoppingapp.domain.EditShopItemUseCase
import com.example.shoppingapp.domain.GetShopListUseCase
import com.example.shoppingapp.domain.RemoveShopItemUseCase
import com.example.shoppingapp.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    /*
        presentation-слой должен знать всё о domain слое
        и ничего не должен знать о data слое
     */

    private val repository = ShopListRepositoryImpl()

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopListUseCase = RemoveShopItemUseCase(repository)

    //stateFlow
    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(item: ShopItem) {
        removeShopListUseCase.removeShopItem(item)
    }
    fun changeEnableState(item: ShopItem) {
        val newItem = item.copy(enabled = !item.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}