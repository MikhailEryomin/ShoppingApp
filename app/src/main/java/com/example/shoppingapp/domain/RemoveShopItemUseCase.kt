package com.example.shoppingapp.domain

import javax.inject.Inject

/*
    Принцип SOLID.
    S - single responsibility (принцип единой ответственности)
    Означает, что каждый класс должен быть ориентирован на какую-то
    одну задачу (Use-Case)
 */

// Данный use-case должен работать только над удалением элемента из списка покупок
class RemoveShopItemUseCase @Inject constructor (
    private val repository: ShopListRepository
) {

    suspend fun removeShopItem(item: ShopItem) {
        repository.removeShopItem(item)
    }

}