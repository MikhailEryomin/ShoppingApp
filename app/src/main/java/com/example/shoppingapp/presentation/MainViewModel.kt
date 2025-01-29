package com.example.shoppingapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.ShopListRepositoryImpl
import com.example.shoppingapp.domain.EditShopItemUseCase
import com.example.shoppingapp.domain.GetShopListUseCase
import com.example.shoppingapp.domain.RemoveShopItemUseCase
import com.example.shoppingapp.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
): AndroidViewModel(application) {

    /*
        presentation-слой должен знать всё о domain слое
        и ничего не должен знать о data слое
     */

    private val repository = ShopListRepositoryImpl(application)

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopListUseCase = RemoveShopItemUseCase(repository)

    //stateFlow
    val shopList = getShopListUseCase.getShopList()

    // USE-CASES
    fun changeEnableState(item: ShopItem) {
        viewModelScope.launch {
            val newItem = item.copy(enabled = !item.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }
    }

    fun removeShopItem(item: ShopItem) {
        viewModelScope.launch {
            removeShopListUseCase.removeShopItem(item)
        }
    }
}