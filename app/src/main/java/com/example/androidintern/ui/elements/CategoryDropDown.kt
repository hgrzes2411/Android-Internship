package com.example.androidintern.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androidintern.R
import com.example.androidintern.ui.models.ItemCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDown(
    expanded: Boolean,
    selectedCategory: ItemCategory,
    categories: List<ItemCategory>,
    onExpandedChange: (Boolean) -> Unit,
    onCategorySelected: (ItemCategory) -> Unit,
    onDismiss: () -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = onExpandedChange, modifier = Modifier.width(268.dp)
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            value = stringResource(id = selectedCategory.displayName),
            onValueChange = {},
            label = { Text(stringResource(id = R.string.category_label)) },
            readOnly = true,
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss() },
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
        ) {
            categories.forEach { category ->
                DropdownMenuItem(text = { Text(text = stringResource(id = category.displayName)) }, onClick = {
                    onCategorySelected(category)
                })
            }
        }
    }
}