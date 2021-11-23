package com.adamnickle.browser11.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun BrowserIcon(
    icon: ImageBitmap,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
)
{
    var pressed by remember { mutableStateOf(false) }
    val pressedTransition = updateTransition(
        targetState = pressed,
        label = "BrowserIcon - pressedTransition",
    )

    val iconScale by pressedTransition.animateFloat(
        label = "BrowserIcon - iconScale",
    ) {
        if(it) 1.15f else 1.0f
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .requiredWidth(64.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                    },
                    onTap = {
                        onClick()
                    },
                )
            },
    ) {
        Surface(
            color = MaterialTheme.colors.surface,
            shape = CircleShape,
            elevation = 8.dp,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .requiredSize(48.dp)
                .scale(iconScale),
        ) {
            Image(
                icon,
                contentDescription = label,
                modifier = Modifier
                    .fillMaxSize(),
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 4.dp),
        )
    }
}