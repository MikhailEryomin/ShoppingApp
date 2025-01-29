package com.example.shoppingapp.domain

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow

/*
    Принцип SOLID.
    S - single responsibility (принцип единой ответственности)
    Означает, что каждый класс должен быть ориентирован на какую-то
    одну задачу (Use-Case)
 */

//Данный use-case должен работать только над получением списка покупок
class GetShopListUseCase(private val repository: ShopListRepository) {

    fun getShopList(): LiveData<List<ShopItem>> =
        repository.getShopList()

}