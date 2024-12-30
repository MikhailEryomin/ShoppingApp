package com.example.shoppingapp.domain

/*
    Класс, который будет основой
    для всей бизнес логики
 */
data class ShopItem(
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean
)
