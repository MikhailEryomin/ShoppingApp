package com.example.shoppingapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText

class ShopItemActivity : AppCompatActivity() {

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        launchRightMode(screenMode, shopItemId)
    }

    private fun parseIntent() {
        val hasModeExtra = intent.hasExtra(EXTRA_MODE)
        if (!hasModeExtra) throw RuntimeException(getString(R.string.screen_mode_error_msg))

        screenMode = intent.getStringExtra(EXTRA_MODE).toString()
        if (screenMode == EDIT_ITEM_MODE) {
            val hasItemIdExtra = intent.hasExtra(ITEM_ID)
            if (!hasItemIdExtra) throw RuntimeException(getString(R.string.item_id_error_msg))
        }
        shopItemId = intent.getIntExtra(ITEM_ID, -1)
    }

    private fun launchRightMode(screenMode: String, shopItemId: Int) {

        val fragment = when (screenMode) {
            ADD_ITEM_MODE -> ShopItemFragment.newInstanceAdd()
            EDIT_ITEM_MODE -> ShopItemFragment.newInstanceEdit(
                shopItemId
            )
            else -> throw RuntimeException("Illegal screenMode param value")
        }
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()

    }

    companion object {

        private const val MODE_UNKNOWN = "mode_unknown"
        private const val EXTRA_MODE = "extra_mode"
        private const val ITEM_ID = "item_id"
        private const val ADD_ITEM_MODE = "add_item_mode"
        private const val EDIT_ITEM_MODE = "edit_item_mode"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(
                EXTRA_MODE,
                ADD_ITEM_MODE
            )
            return intent
        }

        fun newIntentEditItem(context: Context, itemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(
                EXTRA_MODE,
                EDIT_ITEM_MODE
            )
            intent.putExtra(ITEM_ID, itemId)
            return intent
        }

    }

}