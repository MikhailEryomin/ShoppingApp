package com.example.shoppingapp.domain

import javax.inject.Inject

/*
    Принцип SOLID.
    S - single responsibility (принцип единой ответственности)
    Означает, что каждый класс должен быть ориентирован на какую-то
    одну задачу (Use-Case)
 */

// Данный use-case должен работать только над получением конкретного элемента
class GetShopItemUseCase @Inject constructor (
    private val repository: ShopListRepository
) {

    suspend fun getShopItem(itemId: Int): ShopItem {
        return repository.getShopItem(itemId)
    }

}