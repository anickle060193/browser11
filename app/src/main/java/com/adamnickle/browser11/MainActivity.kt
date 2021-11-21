package com.adamnickle.browser11

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.updateLayoutParams
import com.adamnickle.browser11.ui.theme.Browser11Theme

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        if(BuildConfig.DEBUG)
        {
            val abdEnabled = Settings.Global.getInt(contentResolver, Settings.Global.ADB_ENABLED, 0) == 1
            val usbCharging = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                ?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) == BatteryManager.BATTERY_PLUGGED_USB
            if(abdEnabled && usbCharging)
            {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }

        setContent {
            Browser11Theme {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                            .padding(vertical = 4.dp )
                            .verticalScroll(rememberScrollState()),
                    ) {
                        val browsers = packageManager.queryIntentActivities(Intent(Intent.ACTION_VIEW, intent.data
                            ?: Uri.parse("https://google.con")), PackageManager.MATCH_ALL)

                        for(browser in browsers)
                        {
                            val icon = remember(browser) { browser.loadIcon(packageManager).toBitmap().asImageBitmap() }
                            val label = remember(browser) { browser.loadLabel(packageManager).toString() }
                            Card(
                                elevation = 4.dp,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        val browserIntent = Intent(Intent.ACTION_VIEW, intent.data).apply {
                                            setClassName(browser.activityInfo.packageName, browser.activityInfo.name)
                                        }
                                        startActivity(browserIntent)
                                        finish()
                                    },
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                ) {
                                    Surface(
                                        color = MaterialTheme.colors.surface,
                                        shape = CircleShape,
                                        elevation = 8.dp,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .size(48.dp),
                                    ) {
                                        Image(
                                            icon,
                                            contentDescription = label,
                                        )
                                    }

                                    Text(
                                        text = label,
                                        fontSize = 20.sp,
                                        softWrap = false,
                                        modifier = Modifier
                                            .padding(start = 12.dp, end = 4.dp)
                                            .width(IntrinsicSize.Max),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()

        window.decorView.updateLayoutParams<WindowManager.LayoutParams> {
            gravity = Gravity.BOTTOM or Gravity.FILL_HORIZONTAL
        }
        windowManager.updateViewLayout(window.decorView, window.decorView.layoutParams)
    }
}