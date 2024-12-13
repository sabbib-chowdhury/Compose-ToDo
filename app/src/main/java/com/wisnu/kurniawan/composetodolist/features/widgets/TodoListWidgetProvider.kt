package com.wisnu.kurniawan.composetodolist.features.widgets

import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.wisnu.kurniawan.composetodolist.features.widgets.di.WidgetDependencies
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
class TodoListWidgetProvider : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = TodoListWidget()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val widgetDependecies = EntryPointAccessors.fromApplication<WidgetDependencies>(context)
    }
}
