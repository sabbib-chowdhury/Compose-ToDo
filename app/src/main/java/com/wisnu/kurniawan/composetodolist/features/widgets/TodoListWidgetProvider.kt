package com.wisnu.kurniawan.composetodolist.features.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoListWidgetProvider : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = TodoListWidget()

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("LOG_TAG---", "TodoListWidgetProvider-onEnabled#36: ")
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("LOG_TAG---", "TodoListWidgetProvider-onReceive#44: ${intent.action}")
        CoroutineScope(Dispatchers.IO).launch {
            TodoListWidget().updateAll(context)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds.forEach { appWidgetId ->
            Log.d("LOG_TAG---", "TodoListWidgetProvider-onUpdate#62: $appWidgetId")
            appWidgetManager.updateAppWidgetOptions(appWidgetId, Bundle())
        }
    }
}
