package com.example.shoppingapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.AddShopItemUseCase
import com.example.shoppingapp.domain.EditShopItemUseCase
import com.example.shoppingapp.domain.GetShopItemUseCase
import com.example.shoppingapp.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor (
    private val getShopItemUseCase: GetShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val addShopItemUseCase: AddShopItemUseCase
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseWindow = MutableLiveData<Unit>()
    val shouldCloseWindow: LiveData<Unit>
        get() = _shouldCloseWindow


    fun getShopItem(itemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(itemId)
            _shopItem.value = item
        }
    }

    fun addShopItem(inputName: String, inputCount: String) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isValid = validateString(name, count)
        if (isValid) {
            val item = ShopItem(name, count, true)
            viewModelScope.launch {
                addShopItemUseCase.addShopItem(item)
                Log.d("ADD", "Adding an item completed successfully.")
                finishWork()
            }
        }
    }

    fun editShopItem(inputName: String, inputCount: String) {

        val newName = parseName(inputName)
        val newCount = parseCount(inputCount)
        val newIsValid = validateString(newName, newCount)
        if (newIsValid) {
            _shopItem.value?.let {
                val newItem = it.copy(name = newName, count = newCount)
                viewModelScope.launch {
                    editShopItemUseCase.editShopItem(newItem)
                    finishWork()
                }
            }
        }
    }

    private fun finishWork() {
        _shouldCloseWindow.value = Unit
    }

    private fun parseName(str: String?): String {
        return str?.trim() ?: ""
    }

    private fun parseCount(str: String?): Int {
        return str?.trim()?.toIntOrNull() ?: -1
    }

    private fun validateString(name: String, count: Int): Boolean {
        var result = true
        cleanErrors()
        if (name.isEmpty()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    private fun cleanErrors() {
        _errorInputName.value = false
        _errorInputCount.value = false
    }


}