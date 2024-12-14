package com.wisnu.kurniawan.composetodolist.features.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.util.SizeFCompat
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import com.wisnu.kurniawan.composetodolist.features.todo.all.data.AllEnvironment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TodoListWidgetProvider : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var allEnvironment: AllEnvironment

    override val glanceAppWidget: GlanceAppWidget
        get() {
            Log.d("LOG_TAG---", "TodoListWidgetProvider-#17: $allEnvironment")
            val todoListWidget = TodoListWidget()
            todoListWidget.setAllEnvironment(allEnvironment)
            return todoListWidget
        }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("LOG_TAG---", "TodoListWidgetProvider-onEnabled#36: ")
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("LOG_TAG---", "TodoListWidgetProvider-onReceive#44: ")
        CoroutineScope(Dispatchers.IO).launch {
            TodoListWidget().apply {
                setAllEnvironment(allEnvironment)
                updateAll(context)
            }
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val supportedSizes = listOf(
            SizeFCompat(180.0f, 110.0f),
            SizeFCompat(270.0f, 110.0f),
            SizeFCompat(270.0f, 280.0f)
        )
        for (appWidgetId in appWidgetIds) {
            Log.d("LOG_TAG---", "TodoListWidgetProvider-onUpdate#62: $appWidgetId")
            appWidgetManager.updateAppWidgetOptions(appWidgetId, Bundle())

//            appWidgetManager.updateAppWidget(appWidgetId, supportedSizes) {
//                val layoutId = when (it) {
//                    supportedSizes[0] -> R.layout.widget_weather_forecast_small
//                    supportedSizes[1] -> R.layout.widget_weather_forecast_medium
//                    else -> R.layout.widget_weather_forecast_large
//                }
//                RemoteViews(context.packageName, layoutId)
//            }
        }
    }
}
