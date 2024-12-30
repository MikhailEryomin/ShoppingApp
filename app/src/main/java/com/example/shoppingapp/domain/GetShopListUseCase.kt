package com.example.shoppingapp.domain

/*
    Принцип SOLID.
    S - single responsibility (принцип единой ответственности)
    Означает, что каждый класс должен быть ориентирован на какую-то
    одну задачу (Use-Case)
 */

//Данный use-case должен работать только над получением списка покупок
class GetShopListUseCase(private val repository: ShopListRepository) {

    fun getShopListUseCase(): List<ShopItem> =
        repository.getShopList()

}