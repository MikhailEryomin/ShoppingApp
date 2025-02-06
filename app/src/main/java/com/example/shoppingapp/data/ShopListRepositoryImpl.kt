package com.example.shoppingapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoppingapp.domain.ShopItem
import com.example.shoppingapp.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
): ShopListRepository {

//    private val shopListDao = AppDataBase.getInstance(application).shopListDao()
//    private val mapper = ShopListMapper()

    override suspend fun addShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.entityToDbModel(item))
    }

    override suspend fun editShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.entityToDbModel(item))
    }

    override suspend fun removeShopItem(item: ShopItem) {
        shopListDao.removeShopItem(item.id)
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