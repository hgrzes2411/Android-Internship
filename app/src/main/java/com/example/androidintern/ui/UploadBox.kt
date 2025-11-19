package com.example.androidintern.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidintern.R
import com.example.androidintern.ui.theme.TitleBarColor

@Composable
fun UploadBox(modifier: Modifier = Modifier, onUploadClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(180.dp)
            .clickable { onUploadClick() }
            .padding(2.dp) // space for a line
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                val dashPathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

                // background inside the frame
                drawRoundRect(
                    color = Color.White,
                    size = size.copy(width = size.width - strokeWidth, height = size.height - strokeWidth),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )

                // dashed frame
                drawRoundRect(
                    color = Color.Gray,
                    size = size,
                    cornerRadius = CornerRadius(16.dp.toPx()),
                    style = Stroke(width = strokeWidth, pathEffect = dashPathEffect)
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = com.example.androidintern.R.drawable.upload),
                contentDescription = stringResource(id = R.string.upload_icon_description),
                tint = TitleBarColor,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.upload_box_text),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun UploadBoxPreview() {
    UploadBox(onUploadClick = {})
}
