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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.updateLayoutParams
import com.adamnickle.browser11.ui.BrowserSelector
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

        val browsers = packageManager
            .queryIntentActivities(
                Intent(Intent.ACTION_VIEW, intent.data ?: Uri.parse("https://google.con")),
                PackageManager.MATCH_ALL
            )
            .filter { it.activityInfo.packageName != packageName }
            .sortedBy { it.preferredOrder }

        setContent {
            Browser11Theme {
                BrowserSelector(
                    browsers = browsers,
                    loadBrowserIcon = { it.loadIcon(packageManager).toBitmap().asImageBitmap() },
                    loadBrowserLabel = { it.loadLabel(packageManager).toString() },
                    onBrowserClick = {
                        val data = if(intent.action == Intent.ACTION_VIEW) intent.data else null
                        val browserIntent = Intent(Intent.ACTION_VIEW, data).apply {
                            setClassName(it.activityInfo.packageName, it.activityInfo.name)
                        }
                        startActivity(browserIntent)
                        finish()
                    },
                )
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