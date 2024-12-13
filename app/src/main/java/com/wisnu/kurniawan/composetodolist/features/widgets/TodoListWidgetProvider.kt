package com.wisnu.kurniawan.composetodolist.features.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoListWidgetProvider : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = TodoListWidget()
}

class TodoListWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode
        get() = SizeMode.Responsive(
            setOf(
                DpSize(110.dp, 50.dp),
                DpSize(220.dp, 150.dp)
            )
        )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            TodoWidget()
        }

    }
}

@Composable
fun TodoWidget() {
    Column(
        modifier = GlanceModifier.fillMaxSize().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Todo Widget",
            modifier = GlanceModifier.padding(bottom = 8.dp),
        )
        for (i in 1..5) {
            Text(text = "Item $i")
        }
    }
}
