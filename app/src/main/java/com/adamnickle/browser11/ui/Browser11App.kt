package com.adamnickle.browser11.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.adamnickle.browser11.ui.theme.Browser11Theme

@Composable
fun Browser11App(
    activity: Activity,
)
{
    val browsers = remember(activity.packageManager, activity.intent) {
        activity.packageManager
            .queryIntentActivities(
                Intent(Intent.ACTION_VIEW, activity.intent.data ?: Uri.parse("https://google.con")),
                PackageManager.MATCH_ALL
            )
            .filter { it.activityInfo.packageName != activity.packageName }
            .sortedWith(
                compareByDescending<ResolveInfo> { it.preferredOrder }
                    .thenByDescending { it.priority }
            )
    }

    Browser11Theme {
        BrowserSelector(
            browsers = browsers,
            loadBrowserIcon = { it.loadIcon(activity.packageManager).toBitmap().asImageBitmap() },
            loadBrowserLabel = { it.loadLabel(activity.packageManager).toString() },
            onBrowserClick = {
                val data = if(activity.intent.action == Intent.ACTION_VIEW) activity.intent.data else null
                val browserIntent = Intent(Intent.ACTION_VIEW, data).apply {
                    setClassName(it.activityInfo.packageName, it.activityInfo.name)
                }
                activity.startActivity(browserIntent)
                activity.finish()
            },
        )
    }
}