package com.wisnu.kurniawan.composetodolist.features.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
import androidx.glance.unit.ColorProvider
import com.wisnu.kurniawan.composetodolist.R

@Composable
internal fun TodoListHeader(
    todoListId: String,
    todoListName: String,
    todoListColor: Color,
    taskRowBackgroundColor: Color,
    widgetTextStyle: Color,
    onAddTaskClick: (String) -> Action,
) {
    Row(
        modifier = GlanceModifier
            .background(todoListColor)
            .cornerRadius(4.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = GlanceModifier
                .size(36.dp)
                .cornerRadius(18.dp)
                .background(taskRowBackgroundColor)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = GlanceModifier
                    .size(36.dp)
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                provider = ImageProvider(R.drawable.ic_widget_more_horiz),
                contentDescription = todoListName
            )
        }
        Text(
            modifier = GlanceModifier
                .wrapContentWidth()
                .padding(8.dp),
            text = todoListName,
            style = TextStyle(
                color = ColorProvider(widgetTextStyle),
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
            )
        )
        Spacer(modifier = GlanceModifier.defaultWeight())
        Image(
            modifier = GlanceModifier
                .size(36.dp)
                .padding(horizontal = 4.dp)
                .clickable(onAddTaskClick(todoListId)),
            provider = ImageProvider(R.drawable.ic_add_circle_outline),
            contentDescription = "add task",
        )
    }
}
