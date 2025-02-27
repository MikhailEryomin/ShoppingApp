package com.example.shoppingapp.data

import android.app.Application
import android.content.ContentValues
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoppingapp.domain.ShopItem
import com.example.shoppingapp.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper,
    private val application: Application
): ShopListRepository {

//    private val shopListDao = AppDataBase.getInstance(application).shopListDao()
//    private val mapper = ShopListMapper()

    override suspend fun addShopItem(item: ShopItem) {
        //shopListDao.addShopItem(mapper.entityToDbModel(item))
        application.contentResolver.insert(
            Uri.parse("content://com.example.shoppingapp/shop_items"),
            ContentValues().apply {
                put("id", item.id)
                put("name", item.name)
                put("count", item.count)
                put("enabled", item.enabled)
            }
        )
    }

    override suspend fun editShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.entityToDbModel(item))
//        application.contentResolver.update(
//            Uri.parse("content://com.example.shoppinapp/shop_items"),
//            ContentValues().apply {
//                put("id", item.id)
//                put("name", item.name)
//                put("count", item.count)
//                put("enabled", item.enabled)
//            },
//            null,
//            arrayOf(item.id.toString())
//        )
    }

    override suspend fun removeShopItem(item: ShopItem) {
        //shopListDao.removeShopItem(item.id)
        application.contentResolver.delete(
            Uri.parse("content://com.example.shoppingapp/shop_items"),
            null,
            arrayOf(item.id.toString())
        )
    }

    override suspend fun getShopItem(itemID: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(itemID)
        return mapper.dbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> =
        MediatorLiveData<List<ShopItem>>().apply {
            addSource(shopListDao.getShopList()) {
                value = mapper.dbModelListToEntityList(it)
            }
        }
}