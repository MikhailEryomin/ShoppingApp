package com.example.shoppingapp

import android.app.Application
import com.example.shoppingapp.data.ShopListProvider
import com.example.shoppingapp.di.DaggerApplicationComponent

class AppClass: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(
                this
            )
    }

}