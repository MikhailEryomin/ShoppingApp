package com.example.shoppingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.EditShopItemUseCase
import com.example.shoppingapp.domain.GetShopListUseCase
import com.example.shoppingapp.domain.RemoveShopItemUseCase
import com.example.shoppingapp.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

class MainViewModel @Inject constructor(
    private val editShopItemUseCase: EditShopItemUseCase,
    private val getShopListUseCase: GetShopListUseCase,
    private val removeShopListUseCase: RemoveShopItemUseCase
) : ViewModel() {

    /*
        presentation-слой должен знать всё о domain слое
        и ничего не должен знать о data слое
     */

    //stateFlow
    val shopList = getShopListUseCase.getShopList()

    private val providerScope = CoroutineScope(Dispatchers.IO)

    // USE-CASES
    fun changeEnableState(item: ShopItem) {
        viewModelScope.launch {
            val newItem = item.copy(enabled = !item.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }
    }

    fun removeShopItem(item: ShopItem) {
        providerScope.launch {
            removeShopListUseCase.removeShopItem(item)
        }
    }

    override fun onCleared() {
        super.onCleared()
        providerScope.cancel()
    }
}