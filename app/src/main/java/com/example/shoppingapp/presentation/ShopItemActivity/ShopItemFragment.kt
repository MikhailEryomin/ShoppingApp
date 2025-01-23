package com.example.shoppingapp.presentation.ShopItemActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText

class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var shopItem: ShopItem

    private lateinit var nameTextInput: TextInputEditText
    private lateinit var countTextInput: TextInputEditText
    private lateinit var saveButton: Button

    private var errorInputName: Boolean = false
    private var errorInputCount: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        parseParams()
        initViews(view)
        observeViewModel()
        setupSaveButton()
    }

    private fun parseParams() {
        if (screenMode == EDIT_ITEM_MODE) {
            viewModel.getShopItem(itemId = shopItemId)
        }
    }

    private fun initViews(view: View) {
        nameTextInput = view.findViewById(R.id.et_name)
        countTextInput = view.findViewById(R.id.et_count)
        saveButton = view.findViewById(R.id.save_button)
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            errorInputName = it
            if (errorInputName) showError(isNameError = true) else cleanErrors()
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            errorInputCount = it
            if (errorInputCount) showError(isNameError = false) else cleanErrors()
        }
        viewModel.shopItem.observe(viewLifecycleOwner) {
            shopItem = it
            updateTextFields(it.name, it.count.toString())
        }
        viewModel.shouldCloseWindow.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun setupSaveButton() {

        saveButton.setOnClickListener {
            val nameStr = nameTextInput.text.toString()
            val countStr = countTextInput.text.toString()
            when (screenMode) {
                ADD_ITEM_MODE -> viewModel.addShopItem(
                    nameStr,
                    countStr
                )

                EDIT_ITEM_MODE -> viewModel.editShopItem(
                    nameStr,
                    countStr
                )
            }
        }

    }

    private fun updateTextFields(nameInput: String?, countInput: String?) {
        nameTextInput.setText(nameInput)
        countTextInput.setText(countInput)
    }

    private fun showError(isNameError: Boolean) {
        when (isNameError) {
            true -> nameTextInput.error = "Invalid name!"
            false -> countTextInput.error = "Invalid count!"
        }
    }

    private fun cleanErrors() {
        nameTextInput.error = null
        countTextInput.error = null
    }

    companion object {

        private const val MODE_UNKNOWN = "mode_unknown"
        private const val ADD_ITEM_MODE = "add_item_mode"
        private const val EDIT_ITEM_MODE = "edit_item_mode"

        fun newInstanceAdd(): ShopItemFragment {
            return ShopItemFragment(ADD_ITEM_MODE)
        }

        fun newInstanceEdit(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment(EDIT_ITEM_MODE, shopItemId)
        }

    }

}