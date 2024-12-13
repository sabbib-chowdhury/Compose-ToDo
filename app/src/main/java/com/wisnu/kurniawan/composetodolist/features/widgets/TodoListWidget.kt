package com.wisnu.kurniawan.composetodolist.features.widgets

import android.content.Context
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.DayNightColorProvider
import androidx.glance.color.isNightMode
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.wisnu.kurniawan.composetodolist.foundation.theme.LightColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.NightColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.Shapes
import com.wisnu.kurniawan.composetodolist.foundation.theme.Typography

class TodoListWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode
        get() = SizeMode.Responsive(
            setOf(
                DpSize(110.dp, 50.dp),
            )
        )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        Log.d("LOG_TAG---", "TodoListWidget-provideGlance#40: $id")
        provideContent {
            MaterialTheme(
                colorScheme =
                if (context.isNightMode) (NightColorPalette) else LightColorPalette,
                typography = Typography,
                shapes = Shapes,
            ) {
                TodoWidget()
            }
        }
    }

    @Composable
    fun TodoWidget() {
        val widgetTextStyle = TextStyle(
            color = DayNightColorProvider(
                LightColorPalette.onSurface,
                NightColorPalette.onSurface
            )
        )
        Column(
            modifier = GlanceModifier.fillMaxSize()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = "Todo Widget",
                style = widgetTextStyle,
                modifier = GlanceModifier.padding(bottom = 8.dp),
            )
            for (i in 1..5) {
                Text(text = "Item $i", style = widgetTextStyle)
            }
        }
    }
}
