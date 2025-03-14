package com.example.shoppingapp.di

import android.app.Application
import com.example.shoppingapp.data.AppDataBase
import com.example.shoppingapp.data.ShopListDao
import com.example.shoppingapp.data.ShopListRepositoryImpl
import com.example.shoppingapp.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        //provides
        @Provides
        @ApplicationScope
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDataBase.getInstance(application).shopListDao()
        }
    }

}