package com.example.shoppingapp.domain

/*
    Пока мы не знаем как конкретно реализовывать сущность
    которая работает с дата-слоем
    Очевидное решение проблемы - создание интерфейса
    Мы задаем абстрактную сущность репозитория с требуемыми методами.
 */

interface ShopListRepository {

    fun addShopItem(item: ShopItem)

    fun editShopItem(item: ShopItem)

    fun removeShopItem(item: ShopItem)

    fun getShopItem(itemID: Int)

    fun getShopList(): List<ShopItem>

}