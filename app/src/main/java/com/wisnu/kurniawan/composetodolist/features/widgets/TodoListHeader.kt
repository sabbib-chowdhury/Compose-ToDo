package com.wisnu.kurniawan.composetodolist.features.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentWidth
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.foundation.extension.toColor
import com.wisnu.kurniawan.composetodolist.foundation.theme.AlphaMedium
import com.wisnu.kurniawan.composetodolist.model.ToDoList

@Composable
internal fun TodoListHeader(
    todo: ToDoList,
    widgetTextStyle: TextStyle,
    onAddTaskClick: (ToDoList) -> Action,
) {
    Row(
        modifier = GlanceModifier
            .background(todo.color.toColor().copy(alpha = AlphaMedium))
            .cornerRadius(4.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = GlanceModifier
                .size(36.dp)
                .cornerRadius(18.dp)
                .background(todo.color.toColor())
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = GlanceModifier
                    .size(36.dp)
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                provider = ImageProvider(R.drawable.ic_widget_more_horiz),
                contentDescription = todo.name
            )
        }
        val titleSmall = MaterialTheme.typography.titleSmall
        Text(
            modifier = GlanceModifier
                .wrapContentWidth()
                .padding(8.dp),
            text = todo.name,
            style = TextStyle(
                color = widgetTextStyle.color,
                fontSize = titleSmall.fontSize,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
            )
        )
        Spacer(modifier = GlanceModifier.defaultWeight())
        Image(
            modifier = GlanceModifier
                .size(36.dp)
                .padding(horizontal = 4.dp)
                .clickable(onAddTaskClick(todo)),
            provider = ImageProvider(R.drawable.ic_add_circle_outline),
            contentDescription = "add task",
        )
    }
}
