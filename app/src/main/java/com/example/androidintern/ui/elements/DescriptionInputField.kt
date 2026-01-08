package com.example.androidintern.ui.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionInputField(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(id = R.string.description_label)) },
        placeholder = { Text(stringResource(id = R.string.description_placeholder)) },
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        modifier = Modifier
            .width(268.dp)
            .heightIn(min = 110.dp)
            .border(1.dp, MaterialTheme.colorScheme.secondary),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}
