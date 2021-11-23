package com.adamnickle.browser11.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BrowserIcon(
    icon: ImageBitmap,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .requiredWidth(64.dp),
    ) {
        Surface(
            color = MaterialTheme.colors.surface,
            shape = CircleShape,
            elevation = 8.dp,
            onClick = onClick,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .requiredSize(48.dp),
        ) {
            Image(icon, contentDescription = label)
        }

        Text(
            text = label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 4.dp),
        )
    }
}