package com.example.shoppingapp.domain

import androidx.lifecycle.LiveData

/*
    Пока мы не знаем как конкретно реализовывать сущность
    которая работает с дата-слоем
    Очевидное решение проблемы - создание интерфейса
    Мы задаем абстрактную сущность репозитория с требуемыми методами.
 */

interface ShopListRepository {

    suspend fun addShopItem(item: ShopItem)

    suspend fun editShopItem(item: ShopItem)

    suspend fun removeShopItem(item: ShopItem)

    suspend fun getShopItem(itemID: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

}