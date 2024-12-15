package com.wisnu.kurniawan.composetodolist.features.widgets

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.glance.LocalContext
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
import androidx.glance.unit.FixedColorProvider
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.foundation.extension.toColor
import com.wisnu.kurniawan.composetodolist.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.composetodolist.foundation.theme.AlphaMedium
import com.wisnu.kurniawan.composetodolist.foundation.theme.DividerAlpha
import com.wisnu.kurniawan.composetodolist.foundation.uicomponent.itemInfoDisplayable
import com.wisnu.kurniawan.composetodolist.model.ToDoColor
import com.wisnu.kurniawan.composetodolist.model.ToDoStatus
import com.wisnu.kurniawan.composetodolist.model.ToDoTask
import androidx.glance.text.FontWeight as GlanceFontWeight

@Composable
fun TodoItem(
    task: ToDoTask,
    todoColor: ToDoColor,
    onClick: (ToDoTask) -> Unit,
) {
    Column(
        modifier = GlanceModifier
            .background(GlanceTheme.colors.secondaryContainer)
            .clickable { onClick(task) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier.padding(all = 8.dp)
                .fillMaxWidth()
                .height(68.dp)
        ) {

            val (image, color) = if (task.status == ToDoStatus.IN_PROGRESS) {
                R.drawable.ic_widget_round_radio_button_unchecked to ColorProvider(todoColor.toColor())
            } else {
                R.drawable.ic_widget_round_check_circle to ColorProvider(
                    todoColor.toColor().copy(alpha = AlphaDisabled)
                )
            }
            Image(
                provider = ImageProvider(image),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color),
                modifier = GlanceModifier
                    .padding(end = 8.dp)
                    .clickable {
                        onClick(task)
                        Log.d("LOG_TAG---", "-TodoItem#62: $task")
                    }
            )

            Column {
                Text(
                    text = task.name,
                    modifier = GlanceModifier.padding(start = 4.dp),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = GlanceFontWeight.Medium,
                        color = GlanceTheme.colors.onSurface
                    )
                )
                task.itemInfoDisplayable(LocalContext.current.resources, MaterialTheme.colorScheme.error)?.let { info ->
                    Spacer(GlanceModifier.height(4.dp))
                    Text(
                        text = info.text,
                        modifier = GlanceModifier.padding(start = 4.dp),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = GlanceFontWeight.Medium,
                            color = FixedColorProvider(
                                GlanceTheme.colors.onSurface.getColor(LocalContext.current)
                                    .copy(alpha = AlphaMedium)
                            )
                        )
                    )
                }
            }
        }

        Spacer(
            GlanceModifier
                .fillMaxWidth()
                .padding(1.dp)
                .background(
                    GlanceTheme.colors.onSurface
                        .getColor(LocalContext.current)
                        .copy(alpha = DividerAlpha)
                )
        )
    }
}
