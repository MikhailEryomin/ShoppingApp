package com.example.shoppingapp.presentation.MainActivity

import androidx.lifecycle.ViewModel
import com.example.shoppingapp.data.ShopListRepositoryImpl
import com.example.shoppingapp.domain.EditShopItemUseCase
import com.example.shoppingapp.domain.GetShopListUseCase
import com.example.shoppingapp.domain.RemoveShopItemUseCase
import com.example.shoppingapp.domain.ShopItem

class MainViewModel: ViewModel() {

    /*
        presentation-слой должен знать всё о domain слое
        и ничего не должен знать о data слое
     */

    private val repository = ShopListRepositoryImpl

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopListUseCase = RemoveShopItemUseCase(repository)

    //stateFlow
    val shopList = getShopListUseCase.getShopList()

    // USE-CASES

    fun changeEnableState(item: ShopItem) {
        val newItem = item.copy(enabled = !item.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }

    fun removeShopItem(item: ShopItem) {
        removeShopListUseCase.removeShopItem(item)
    }
}