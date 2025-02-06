package com.example.shoppingapp.data

import com.example.shoppingapp.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {

    fun entityToDbModel(shopItem: ShopItem): ShopItemDbModel = ShopItemDbModel(
        id = shopItem.id,
        name =  shopItem.name,
        count = shopItem.count,
        enabled = shopItem.enabled
    )

    fun dbModelToEntity(shopItemDbMode: ShopItemDbModel): ShopItem = ShopItem(
        id = shopItemDbMode.id,
        name =  shopItemDbMode.name,
        count = shopItemDbMode.count,
        enabled = shopItemDbMode.enabled
    )

    fun entityListToDbModelList(shopItemList: List<ShopItem>): List<ShopItemDbModel> =
        shopItemList.map { entityToDbModel(it) }

    fun dbModelListToEntityList(shopItemDbModelList: List<ShopItemDbModel>): List<ShopItem> =
        shopItemDbModelList.map { dbModelToEntity(it) }

}