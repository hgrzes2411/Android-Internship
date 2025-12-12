package com.example.androidintern.ui.models

import androidx.annotation.StringRes
import com.example.androidintern.R

enum class ItemCategory(@StringRes val displayName: Int) {
    CATEGORY_1(R.string.category_1),
    CATEGORY_2(R.string.category_2),
    CATEGORY_3(R.string.category_3)
}
