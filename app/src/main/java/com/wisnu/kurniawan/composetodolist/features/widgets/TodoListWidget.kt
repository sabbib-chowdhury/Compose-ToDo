package com.wisnu.kurniawan.composetodolist.features.widgets

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.lazy.items
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
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.features.todo.all.data.AllEnvironment
import com.wisnu.kurniawan.composetodolist.foundation.extension.toColor
import com.wisnu.kurniawan.composetodolist.foundation.theme.LightColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.NightColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.Shapes
import com.wisnu.kurniawan.composetodolist.foundation.theme.Typography
import com.wisnu.kurniawan.composetodolist.model.ToDoList
import com.wisnu.kurniawan.composetodolist.model.ToDoStatus

class TodoListWidget : GlanceAppWidget() {

    private lateinit var allEnvironment: AllEnvironment
    override val sizeMode: SizeMode
        get() = SizeMode.Responsive(
            setOf(
                DpSize(110.dp, 50.dp),
            )
        )

    private val widgetTextStyle = TextStyle(
        color = DayNightColorProvider(
            LightColorPalette.onSurface,
            NightColorPalette.onSurface
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        Log.d("LOG_TAG---", "TodoListWidget-provideGlance#46: ")
        val todoList = allEnvironment.getList()
        todoList.collect {
            provideContent {
                MaterialTheme(
                    colorScheme = if (context.isNightMode) NightColorPalette else LightColorPalette,
                    typography = Typography,
                    shapes = Shapes,
                ) {
                    TodoWidget(context.getString(R.string.todo_all), it)
                }
            }
        }
    }

    @Composable
    fun TodoWidget(
        toDoListName: String,
        toDoLists: List<ToDoList> = emptyList()
    ) {
        Column(
            modifier = GlanceModifier.fillMaxSize()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = toDoListName,
                style = widgetTextStyle,
                modifier = GlanceModifier.padding(bottom = 8.dp),
            )
            TodoList(toDoLists)
        }
    }

    @Composable
    private fun TodoList(
        toDoLists: List<ToDoList>,
    ) {
        toDoLists.forEach { todo ->
            Text(
                modifier = GlanceModifier.background(todo.color.toColor()),
                text = todo.name,
                style = widgetTextStyle
            )
            LazyColumn(
                modifier = GlanceModifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                items(todo.tasks) { item ->
                    if (item.status == ToDoStatus.IN_PROGRESS)
                        TodoItem(item.name)
                }
            }
        }
    }

    @Composable
    private fun TodoItem(name: String) {
        Text(text = name, style = widgetTextStyle)
    }

    internal fun setAllEnvironment(allEnvironment: AllEnvironment) {
        this.allEnvironment = allEnvironment
    }
}
