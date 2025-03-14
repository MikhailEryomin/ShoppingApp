package com.example.shoppingapp.domain

/*
    Класс, который будет основой
    для всей бизнес логики
 */
data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID,
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
