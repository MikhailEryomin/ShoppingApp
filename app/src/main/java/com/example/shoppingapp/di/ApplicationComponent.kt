package com.example.shoppingapp.di

import android.app.Application
import com.example.shoppingapp.data.ShopListProvider
import com.example.shoppingapp.presentation.MainActivity
import com.example.shoppingapp.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    //inject
    fun inject(mainActivity: MainActivity)

    fun inject(shopItemFragment: ShopItemFragment)

    fun inject(provider: ShopListProvider)

    @Component.Factory
    interface Factory {
        fun create(
            //params
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}