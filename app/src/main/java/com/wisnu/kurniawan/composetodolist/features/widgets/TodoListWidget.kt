package com.wisnu.kurniawan.composetodolist.features.widgets

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.action.Action
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.color.DayNightColorProvider
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.TextStyle
import com.wisnu.kurniawan.composetodolist.features.widgets.domain.AllListWidgetInteractor
import com.wisnu.kurniawan.composetodolist.features.widgets.model.AllTodoListWidgetUiContract
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.mapper.toGlanceColorProviders
import com.wisnu.kurniawan.composetodolist.foundation.extension.toToDoColor
import com.wisnu.kurniawan.composetodolist.foundation.theme.AlphaMedium
import com.wisnu.kurniawan.composetodolist.foundation.theme.LightColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.NightColorPalette
import com.wisnu.kurniawan.composetodolist.model.Theme
import com.wisnu.kurniawan.composetodolist.model.ToDoTask
import com.wisnu.kurniawan.composetodolist.runtime.MainActivity
import com.wisnu.kurniawan.composetodolist.runtime.navigation.ListDetailFlow
import com.wisnu.kurniawan.composetodolist.runtime.navigation.WidgetSettings
import kotlinx.coroutines.launch

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
            val actionState by interactor.actionState.collectAsState()
            context.HandleActionState(id, actionState)
            val todoLists by interactor.allLists.collectAsState()
            val hostEnvironmentState by interactor.themes.collectAsState(Theme.SYSTEM)
            val themeColors = hostEnvironmentState.toGlanceColorProviders()
            GlanceTheme(themeColors) {
                Scaffold(
                    titleBar = { AllListTitleBar() }
                ) {
                    TodoWidget(
                        todoLists,
                        widgetTextColor = widgetTextStyle.color.getColor(context),
                        onClick = {
                            coroutineScope.launch {
                                interactor.toggleTaskStatus(it)
                            }
                        },
                        onAddTaskClick = { listId ->
                            actionStartActivity(createMainActivityIntent(context, listId))
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun Context.HandleActionState(
        id: GlanceId,
        actionState: AllTodoListWidgetUiContract.Actions,
    ) {
        val actionStateScope = rememberCoroutineScope()
        LaunchedEffect(id, actionState) {
            actionStateScope.launch {
                when(actionState) {
                    is AllTodoListWidgetUiContract.Actions.UpdateWidget -> {
                        updateAll(this@HandleActionState)
                    }
                }
            }
        }
    }

    private fun createMainActivityIntent(context: Context, routeId: String): Intent {
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
}

@Composable
fun TodoWidget(
    toDoLists: AllTodoListWidgetUiContract.WidgetUiModel =
        AllTodoListWidgetUiContract.WidgetUiModel(
            emptyList()
        ),
    widgetTextColor: Color = GlanceTheme.colors.onSurface.getColor(LocalContext.current),
    onClick: (ToDoTask) -> Unit,
    onAddTaskClick: (String) -> Action,
) {
    TodoList(toDoLists, widgetTextColor, onClick, onAddTaskClick)
}

@Composable
private fun TodoList(
    toDoLists: AllTodoListWidgetUiContract.WidgetUiModel,
    widgetTextColor: Color,
    onClick: (ToDoTask) -> Unit,
    onAddTaskClick: (String) -> Action,
) {
    LazyColumn(
        modifier = GlanceModifier.fillMaxSize()
    ) {
        toDoLists.data.forEach { todo ->
            item {
                TodoListHeader(
                    todoListId = todo.todoListId,
                    todoListName = todo.todoListName,
                    todoListColor = todo.todoListColor.copy(alpha = AlphaMedium),
                    taskRowBackgroundColor = todo.taskRowBackgroundColor,
                    widgetTextStyle = widgetTextColor,
                    onAddTaskClick = onAddTaskClick,
                )
            }
            items(todo.todoTasks) { task ->
                TodoItem(task.taskName, task.taskDetailInfo, task.taskStatus, todo.todoListColor.toToDoColor(), onClick)
            }
        }
    }
}
