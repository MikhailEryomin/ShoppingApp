package com.example.shoppingapp.domain

/*
    Принцип SOLID.
    S - single responsibility (принцип единой ответственности)
    Означает, что каждый класс должен быть ориентирован на какую-то
    одну задачу (Use-Case)
 */

// Данный use-case должен работать только над удалением элемента из списка покупок
class RemoveShopItemUseCase(private val repository: ShopListRepository) {

    suspend fun removeShopItem(item: ShopItem) {
        repository.removeShopItem(item)
    }

}