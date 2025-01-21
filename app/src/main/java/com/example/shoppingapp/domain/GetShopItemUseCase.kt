package com.example.shoppingapp.domain

/*
    Принцип SOLID.
    S - single responsibility (принцип единой ответственности)
    Означает, что каждый класс должен быть ориентирован на какую-то
    одну задачу (Use-Case)
 */

// Данный use-case должен работать только над получением конкретного элемента
class GetShopItemUseCase(private val repository: ShopListRepository) {

    fun getShopItem(itemId: Int): ShopItem? {
        return repository.getShopItem(itemId)
    }

}