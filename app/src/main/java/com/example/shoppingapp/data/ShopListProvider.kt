package com.example.shoppingapp.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.shoppingapp.AppClass
import com.example.shoppingapp.domain.ShopItem
import javax.inject.Inject

class ShopListProvider : ContentProvider() {

    @Inject
    lateinit var shopItemDao: ShopListDao

    @Inject
    lateinit var mapper: ShopListMapper

    private val component by lazy {
        (context as AppClass).component
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.shoppingapp", "shop_items", GET_SHOP_ITEMS_QUERY)
        addURI("com.example.shoppingapp", "shop_items/#", GET_SHOP_ITEM_QUERY_ID)
    }

    override fun onCreate(): Boolean {
        //content provider is successfully created
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val code = uriMatcher.match(uri)
        return when (code) {
            GET_SHOP_ITEMS_QUERY -> {
                shopItemDao.getShopListCursor()
            }

            else -> {
                null
            }
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        when (uriMatcher.match(uri)) {

            GET_SHOP_ITEMS_QUERY -> {
                if (values == null) return null
                with(values) {
                    val id = getAsInteger("id")
                    val name = getAsString("name")
                    val count = getAsInteger("count")
                    val enabled = getAsBoolean("enabled")
                    val shopItem = ShopItem(
                        id = id,
                        name = name,
                        count = count,
                        enabled = enabled
                    )
                    shopItemDao.addShopItemNotSuspend(
                        mapper.entityToDbModel(shopItem)
                    )
                }
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return shopItemDao.removeShopItemNotSuspend(id)
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    companion object {
        private const val GET_SHOP_ITEMS_QUERY = 100
        private const val GET_SHOP_ITEM_QUERY_ID = 101
    }
}