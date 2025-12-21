package com.sopt.dive.presentation.util

import android.app.Activity
import android.content.ComponentName
import android.content.pm.PackageManager
import android.util.Log
import com.sopt.dive.presentation.type.AppIcon

fun Activity.changeIcon(
    targetIcon: AppIcon
) {
    Log.d("ICON", "⭐⭐⭐")
    AppIcon.entries.forEach { icon ->
        val state = if (icon == targetIcon) {
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        } else {
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        }

        packageManager.setComponentEnabledSetting(
            ComponentName(
                this,
                icon.componentName
            ),
            state,
            PackageManager.DONT_KILL_APP
        )

        if (state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            Log.d("ICON", "$icon 활성화됨")
        }
    }
}
