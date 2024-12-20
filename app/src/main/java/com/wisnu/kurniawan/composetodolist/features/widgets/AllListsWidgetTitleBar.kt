package com.wisnu.kurniawan.composetodolist.features.widgets

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.layout.padding
import androidx.glance.layout.size
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.runtime.MainActivity
import com.wisnu.kurniawan.composetodolist.runtime.navigation.WidgetSettings

@Composable
fun AllListTitleBar(modifier: GlanceModifier = GlanceModifier) {
    TitleBar(
        startIcon = ImageProvider(R.drawable.ic_logo),
        iconColor = null,
        title = LocalContext.current.getString(R.string.todo_all),
    ) {
        val packageName = LocalContext.current.packageName
        val MainActivityIntent = Intent(
            LocalContext.current,
            MainActivity::class.java
        ).apply {
            putExtra(
                "$packageName${WidgetSettings.WidgetSettingsScreen.actionKeySuffix}",
                WidgetSettings.WidgetSettingsScreen.route,
            )
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            action = WidgetSettings.WidgetSettingsScreen.route
        }
        Image(
            modifier = modifier
                .padding(end = 12.dp)
                .size(32.dp)
                .clickable(actionStartActivity(MainActivityIntent)),
            provider = ImageProvider(R.drawable.ic_widget_settings),
            contentDescription = "settings"
        )
    }
}

@Preview
@Composable
private fun AllListTitleBarPreview() {
    AllListTitleBar()
}
