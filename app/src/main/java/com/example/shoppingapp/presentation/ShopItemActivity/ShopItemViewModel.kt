package com.example.shoppingapp.presentation.ShopItemActivity

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.data.ShopListRepositoryImpl
import com.example.shoppingapp.domain.AddShopItemUseCase
import com.example.shoppingapp.domain.EditShopItemUseCase
import com.example.shoppingapp.domain.GetShopItemUseCase
import com.example.shoppingapp.domain.ShopItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ShopItemViewModel : ViewModel() {

    // USE-CASES:

    /*
    1) getting an item by id
    2) editing an item
    3) adding an item
     */

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _selectedShopItem = MutableLiveData<ShopItem>()
    val selectedShopItem: LiveData<ShopItem>
        get() = _selectedShopItem

    private val _shouldCloseWindow = MutableLiveData<Unit>()
    val shouldCloseWindow: LiveData<Unit>
        get() = _shouldCloseWindow

    private val repository = ShopListRepositoryImpl
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    fun getShopItem(itemId: Int) {
        val item = getShopItemUseCase.getShopItem(itemId)
        _selectedShopItem.value = item
    }

    fun addShopItem(inputName: String, inputCount: String) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isValid = validateString(name, count)
        if (isValid) {
            val item = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(item)
            Log.d("ADD", "Adding an item completed successfully.")
            finishWork()
        }
    }

    fun editShopItem(inputName: String, inputCount: String) {

        val newName = parseName(inputName)
        val newCount = parseCount(inputCount)
        val newIsValid = validateString(newName, newCount)
        if (newIsValid) {
            _selectedShopItem.value?.let {
                val newItem = it.copy(name = newName, count = newCount)
                editShopItemUseCase.editShopItem(newItem)
                finishWork()
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