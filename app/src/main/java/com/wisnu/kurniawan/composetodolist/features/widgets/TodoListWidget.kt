package com.wisnu.kurniawan.composetodolist.features.widgets

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.Action
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.color.DayNightColorProvider
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.TextStyle
import com.wisnu.kurniawan.composetodolist.features.widgets.domain.AllListWidgetInteractor
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.mapper.toGlanceColorProviders
import com.wisnu.kurniawan.composetodolist.foundation.theme.LightColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.NightColorPalette
import com.wisnu.kurniawan.composetodolist.model.Theme
import com.wisnu.kurniawan.composetodolist.model.ToDoColor
import com.wisnu.kurniawan.composetodolist.model.ToDoList
import com.wisnu.kurniawan.composetodolist.model.ToDoStatus
import com.wisnu.kurniawan.composetodolist.model.ToDoTask
import com.wisnu.kurniawan.composetodolist.runtime.MainActivity
import com.wisnu.kurniawan.composetodolist.runtime.navigation.ListDetailFlow
import com.wisnu.kurniawan.composetodolist.runtime.navigation.WidgetSettings
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TodoListWidget : GlanceAppWidget() {

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
        val interactor = AllListWidgetInteractor.get(context)

        provideContent {
            val coroutineScope = rememberCoroutineScope()
            val todoLists by interactor.allLists.collectAsState(emptyList())
            val hostEnvironmentState by interactor.themes.collectAsState(Theme.SYSTEM)
            val themeColors = hostEnvironmentState.toGlanceColorProviders()
            GlanceTheme(themeColors) {
                Scaffold(
                    titleBar = { AllListTitleBar() }
                ) {

                    fun createMainActivityIntent(context: Context, routeId: String): Intent {
                        val packageName = context.packageName
                        val mainActivityIntent = Intent(context, MainActivity::class.java)
                        mainActivityIntent.apply {
                            putExtra(
                                "$packageName${WidgetSettings.WidgetSettingsScreen.actionKeySuffix}",
                                ListDetailFlow.Root.route(routeId),
                            )
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            action = ListDetailFlow.Root.route(routeId)
                        }
                        return mainActivityIntent
                    }

                    TodoWidget(
                        todoLists,
                        widgetTextStyle = widgetTextStyle,
                        onClick = {
                            coroutineScope.launch {
                                interactor.toggleTaskStatus(it)
                            }
                        },
                        onAddTaskClick = { todoList ->
                            actionStartActivity(createMainActivityIntent(context, todoList.id))
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun TodoWidget(
    toDoLists: List<ToDoList> = emptyList(),
    widgetTextStyle: TextStyle = TextStyle(),
    onClick: (ToDoTask) -> Unit,
    onAddTaskClick: (ToDoList) -> Action,
) {
    TodoList(toDoLists, widgetTextStyle, onClick, onAddTaskClick)
}

@Composable
private fun TodoList(
    toDoLists: List<ToDoList>,
    widgetTextStyle: TextStyle,
    onClick: (ToDoTask) -> Unit,
    onAddTaskClick: (ToDoList) -> Action,
) {
    LazyColumn(
        modifier = GlanceModifier.fillMaxSize()
    ) {
        toDoLists.forEach { todo ->
            item {
                TodoListHeader(todo, widgetTextStyle, onAddTaskClick)
            }
            items(todo.tasks) { item ->
                TodoItem(item, todo.color, onClick)
            }
        }
    }
}

@Preview
@Composable
private fun TodoWidgetPreview() {
    GlanceTheme {
        TodoWidget(
            toDoLists = listOf(
                ToDoList(
                    id = "Work",
                    name = "todo preview",
                    color = ToDoColor.RED,
                    tasks = listOf(
                        ToDoTask(
                            "",
                            "Task 1",
                            ToDoStatus.IN_PROGRESS,
                            createdAt = LocalDateTime.now(),
                            updatedAt = LocalDateTime.now()
                        ),
                    ),
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
            ),
            widgetTextStyle = TextStyle(
                color = DayNightColorProvider(
                    LightColorPalette.onSurface,
                    NightColorPalette.onSurface
                )
            ),
            onClick = {},
            onAddTaskClick = { object : Action {} }
        )
    }
}
