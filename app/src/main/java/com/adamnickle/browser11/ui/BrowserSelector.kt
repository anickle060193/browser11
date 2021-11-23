package com.adamnickle.browser11.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

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
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 12.dp)
                .horizontalScroll(rememberScrollState()),
        ) {

            for(browser in browsers)
            {
                BrowserIcon(
                    icon = remember(browser) { loadBrowserIcon(browser) },
                    label = remember(browser) { loadBrowserLabel(browser) },
                    onClick = { onBrowserClick(browser) },
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}