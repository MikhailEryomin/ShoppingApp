package com.example.shoppingapp.di

import androidx.lifecycle.ViewModel
import com.example.shoppingapp.presentation.MainViewModel
import com.example.shoppingapp.presentation.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(shopItemViewModel: ShopItemViewModel): ViewModel

}