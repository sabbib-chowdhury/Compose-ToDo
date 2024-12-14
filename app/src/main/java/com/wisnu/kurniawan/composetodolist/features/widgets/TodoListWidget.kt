package com.wisnu.kurniawan.composetodolist.features.widgets

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProviders
import androidx.glance.color.DayNightColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.features.widgets.domain.AllListWidgetInteractor
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.mapper.isDarkMode
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.mapper.toColorProviders
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.mapper.toColorScheme
import com.wisnu.kurniawan.composetodolist.foundation.extension.toColor
import com.wisnu.kurniawan.composetodolist.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.composetodolist.foundation.theme.LightColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.NightColorPalette
import com.wisnu.kurniawan.composetodolist.model.Theme
import com.wisnu.kurniawan.composetodolist.model.ToDoList
import com.wisnu.kurniawan.composetodolist.model.ToDoStatus
import com.wisnu.kurniawan.composetodolist.model.ToDoTask
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
        Log.d("LOG_TAG---", "TodoListWidget-provideGlance#46: ")
        val interactor = AllListWidgetInteractor.get(context)

        provideContent {
            val coroutineScope = rememberCoroutineScope()
            val todoLists by interactor.allLists.collectAsState(emptyList())
            val hostEnvironmentState by interactor.themes.collectAsState(Theme.SYSTEM)
            val themeColors = hostEnvironmentState.toColorScheme(context).toColorProviders(context)
            Log.d(
                "LOG_TAG---",
                "TodoListWidget-provideGlance#65: ${context.isDarkMode} ${themeColors.background}"
            )
            GlanceTheme(themeColors) {
                TodoWidget(
                    context.getString(R.string.todo_all),
                    todoLists,
                    themeColors,
                    context
                ) {
                    coroutineScope.launch {
                        interactor.toggleTaskStatus(it)
                    }
                }
            }
        }
    }

    @Composable
    private fun TodoWidget(
        toDoListName: String,
        toDoLists: List<ToDoList> = emptyList(),
        themeColorProvider: ColorProviders,
        context: Context = LocalContext.current,
        onClick: (ToDoTask) -> Unit,
    ) {
        Column(
            modifier = GlanceModifier.fillMaxSize()
                .padding(8.dp)
                .background(themeColorProvider.surface),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = toDoListName,
                style = widgetTextStyle.copy(color = themeColorProvider.onSurface),
                modifier = GlanceModifier.padding(bottom = 8.dp),
            )
            TodoList(toDoLists, themeColorProvider, context, onClick)
        }
    }

    @Composable
    private fun TodoList(
        toDoLists: List<ToDoList>,
        themeColorProvider: ColorProviders,
        context: Context = LocalContext.current,
        onClick: (ToDoTask) -> Unit,
    ) {
        LazyColumn(
            modifier = GlanceModifier.fillMaxSize()
                .background(themeColorProvider.secondaryContainer)
        ) {
            toDoLists.forEach { todo ->
                item {
                    Log.d("LOG_TAG---", "TodoListWidget-TodoList#119: ${todo.name}")
                    Row(
                        GlanceModifier.background(todo.color.toColor().copy(alpha = AlphaDisabled))
                    ) {
                        Box(
                            GlanceModifier.cornerRadius(120.dp)
                                .wrapContentSize()
                                .background(todo.color.toColor()),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = GlanceModifier
                                    .padding(vertical = 16.dp, horizontal = 8.dp),
                                colorFilter = ColorFilter.tint(
                                    ColorProvider(Color.White.copy(alpha = AlphaDisabled))
                                ),
                                provider = ImageProvider(R.drawable.ic_widget_more_horiz),
                                contentDescription = todo.name
                            )
                        }
                        Text(
                            modifier = GlanceModifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 8.dp),
                            text = todo.name,
                            style = widgetTextStyle
                        )
                    }
                }
                items(todo.tasks) { item ->
                    if (item.status == ToDoStatus.IN_PROGRESS)
                        TodoItem(item, todo.color, widgetTextStyle, context, onClick)
                }
            }
        }
    }
}
