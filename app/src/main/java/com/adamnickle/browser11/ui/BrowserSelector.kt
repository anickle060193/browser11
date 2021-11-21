package com.adamnickle.browser11.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> BrowserSelector(
    browsers: List<T>,
    loadBrowserIcon: (T) -> ImageBitmap,
    loadBrowserLabel: (T) -> String,
    onBrowserClick: (T) -> Unit,
)
{
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(horizontal = 4.dp, vertical = 12.dp)
                .horizontalScroll(rememberScrollState()),
        ) {

            for(browser in browsers)
            {
                val icon = remember(browser) { loadBrowserIcon(browser) }
                val label = remember(browser) { loadBrowserLabel(browser) }
                Surface(
                    color = MaterialTheme.colors.surface,
                    shape = CircleShape,
                    elevation = 8.dp,
                    onClick = {
                        onBrowserClick(browser)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 4.dp)
                        .size(48.dp),
                ) {
                    Image(icon, contentDescription = label)
                }
            }
        }
    }
}