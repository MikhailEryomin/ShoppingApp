package com.example.shoppingapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentShopItemBinding
import com.example.shoppingapp.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText

class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding = null")


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
    ): View {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        parseParams()
        initViews()
        observeViewModel()
        setupSaveButton()
    }

    private fun parseParams() {
        if (screenMode == EDIT_ITEM_MODE) {
            viewModel.getShopItem(itemId = shopItemId)
        }
    }

    private fun initViews() {
        nameTextInput = binding.etName
        countTextInput = binding.etCount
        saveButton = binding.saveButton
    }

    private fun observeViewModel() {
        binding.viewModel = viewModel
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