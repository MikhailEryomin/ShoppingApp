package com.example.shoppingapp.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppingapp.domain.ShopItem

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_items")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    @Query("SELECT * FROM shop_items")
    fun getShopListCursor(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItem: ShopItemDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItemNotSuspend(shopItem: ShopItemDbModel)

    @Query("DELETE FROM shop_items WHERE id=:shopItemId")
    suspend fun removeShopItem(shopItemId: Int)

    @Query("DELETE FROM shop_items WHERE id=:shopItemId")
    fun removeShopItemNotSuspend(shopItemId: Int): Int

    @Query("SELECT * FROM shop_items WHERE id=:shopItemId LIMIT 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDbModel

}