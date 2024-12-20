package com.wisnu.kurniawan.composetodolist.features.widgets

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
import com.wisnu.kurniawan.composetodolist.features.widgets.model.TaskStatusUi
import com.wisnu.kurniawan.composetodolist.foundation.extension.toColor
import com.wisnu.kurniawan.composetodolist.foundation.theme.DividerAlpha
import com.wisnu.kurniawan.composetodolist.foundation.uicomponent.itemInfoDisplayable
import com.wisnu.kurniawan.composetodolist.model.ToDoColor
import com.wisnu.kurniawan.composetodolist.model.ToDoTask
import androidx.glance.text.FontWeight as GlanceFontWeight

@Composable
fun TodoItem(
    taskName: String,
    taskDetailInfo: ToDoTask,
    taskStatusUi: TaskStatusUi,
    todoColor: ToDoColor,
    onClick: (ToDoTask) -> Unit,
) {
    Column(
        modifier = GlanceModifier
            .clickable { onClick(taskDetailInfo) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier.padding(all = 8.dp)
                .fillMaxWidth()
                .height(68.dp)
        ) {

            Image(
                provider = ImageProvider(taskStatusUi.imageRes),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    ColorProvider(
                        todoColor.toColor()
                            .copy(alpha = taskStatusUi.imageColorFilterAlpha)
                    )
                ),
                modifier = GlanceModifier
                    .padding(end = 8.dp)
                    .clickable { onClick(taskDetailInfo) }
            )

            Column {
                Text(
                    text = taskName,
                    modifier = GlanceModifier.padding(start = 4.dp),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = GlanceFontWeight.Medium,
                        color = ColorProvider(taskStatusUi.taskColor),
                        textDecoration = taskStatusUi.textDecoration
                    )
                )
                taskDetailInfo.itemInfoDisplayable(
                    LocalContext.current.resources,
                    taskStatusUi.taskDetailInfoTextColor
                )?.let { info ->
                    Spacer(GlanceModifier.height(4.dp))
                    Text(
                        text = info.text,
                        modifier = GlanceModifier.padding(start = 4.dp),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = GlanceFontWeight.Medium,
                            color = ColorProvider(taskStatusUi.taskDetailInfoTextColor)
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
