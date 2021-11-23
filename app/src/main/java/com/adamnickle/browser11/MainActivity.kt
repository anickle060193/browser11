package com.adamnickle.browser11

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.updateLayoutParams
import com.adamnickle.browser11.ui.Browser11App

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

        setContent { Browser11App(this) }
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