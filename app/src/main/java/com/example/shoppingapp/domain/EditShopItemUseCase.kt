package com.example.shoppingapp.domain

/*
    Принцип SOLID.
    S - single responsibility (принцип единой ответственности)
    Означает, что каждый класс должен быть ориентирован на какую-то
    одну задачу (Use-Case)))
 */

// Данный use-case должен работать только над изменением элемента в списке покупок
class EditShopItemUseCase(private val repository: ShopListRepository) {

    fun editShopItem(item: ShopItem) {
        repository.editShopItem(item)
    }

}