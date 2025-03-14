package com.example.shoppingapp.domain

import javax.inject.Inject

/*
    Принцип SOLID.
    S - single responsibility (принцип единой ответственности)
    Означает, что каждый класс должен быть ориентирован на какую-то
    одну задачу (Use-Case)
 */

// Данный use-case должен работать только над добавлением элемента в список
class AddShopItemUseCase @Inject constructor(
    private val repository: ShopListRepository
) {

    /*
        Мы создали и описали интерфейс репозитория, который по факту
        должен заниматься всеми операциями
        Поэтому реализация методов в юзкейсе - вызов метода репозитория
     */
    suspend fun addShopItem(item: ShopItem) {
        repository.addShopItem(item)
    }

}