package com.wisnu.kurniawan.composetodolist.features.widgets

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.foundation.extension.toColor
import com.wisnu.kurniawan.composetodolist.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.composetodolist.foundation.theme.DividerAlpha
import com.wisnu.kurniawan.composetodolist.model.ToDoColor
import com.wisnu.kurniawan.composetodolist.model.ToDoStatus

@Composable
fun TodoItem(
    name: String,
    todoStatus: ToDoStatus,
    todoColor: ToDoColor,
    widgetTextStyle: TextStyle,
    context: Context = LocalContext.current
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier.padding(8.dp)
        ) {

            val (image, color) = if (todoStatus == ToDoStatus.IN_PROGRESS) {
                R.drawable.ic_round_radio_button_unchecked to ColorProvider(todoColor.toColor())
            } else {
                R.drawable.ic_round_check_circle to ColorProvider(todoColor.toColor().copy(alpha = AlphaDisabled))
            }
            Image(
                provider = ImageProvider(image),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color),
                modifier = GlanceModifier
                    .padding(end = 4.dp)
                    .clickable {
                        Log.d("LOG_TAG---", "-TodoItem#62: $name")
                    }
            )

            Column {
                Spacer(GlanceModifier.height(4.dp))
                Text(
                    text = name,
                    modifier = GlanceModifier.padding(bottom = 4.dp),
                    style = widgetTextStyle,
//                        style = MaterialTheme.typography.labelMedium,
                )
            }
        }

        Spacer(
            GlanceModifier
                .fillMaxWidth()
                .padding(1.dp)
                .background(
                    GlanceTheme.colors.onSurface.getColor(context).copy(alpha = DividerAlpha)
                )
        )
    }
}
