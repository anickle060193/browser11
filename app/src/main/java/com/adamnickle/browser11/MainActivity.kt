package com.adamnickle.browser11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.adamnickle.browser11.ui.theme.Browser11Theme

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            Browser11Theme {
                Surface(color = MaterialTheme.colors.background) {
                }
            }
        }
    }
}