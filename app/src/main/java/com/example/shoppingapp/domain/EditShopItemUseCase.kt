package com.example.shoppingapp.domain

import javax.inject.Inject

/*
    Принцип SOLID.
    S - single responsibility (принцип единой ответственности)
    Означает, что каждый класс должен быть ориентирован на какую-то
    одну задачу (Use-Case)))
 */

// Данный use-case должен работать только над изменением элемента в списке покупок
class EditShopItemUseCase @Inject constructor (
    private val repository: ShopListRepository
) {

    suspend fun editShopItem(item: ShopItem) {
        repository.editShopItem(item)
    }

}