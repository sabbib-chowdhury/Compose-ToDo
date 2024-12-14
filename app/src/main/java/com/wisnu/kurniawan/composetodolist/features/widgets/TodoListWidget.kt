package com.wisnu.kurniawan.composetodolist.features.widgets

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProviders
import androidx.glance.color.DayNightColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.features.host.data.HostEnvironment
import com.wisnu.kurniawan.composetodolist.features.todo.all.data.AllEnvironment
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.mapper.isDarkMode
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.mapper.toColorProviders
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.mapper.toColorScheme
import com.wisnu.kurniawan.composetodolist.foundation.extension.toColor
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
        val allEnvironment = AllEnvironment.get(context)
        val hostEnvironment = HostEnvironment.get(context)

        val allList = allEnvironment.getList()
        provideContent {
            val coroutineScope = rememberCoroutineScope()
            val todoLists by allList.collectAsState(emptyList())
            val hostEnvironmentState by hostEnvironment.getTheme().collectAsState(Theme.SYSTEM)
            val themeColors = hostEnvironmentState.toColorScheme(context).toColorProviders(context)
            Log.d("LOG_TAG---", "TodoListWidget-provideGlance#65: ${context.isDarkMode} ${themeColors.background}")
            GlanceTheme(themeColors) {
                TodoWidget(
                    context.getString(R.string.todo_all),
                    todoLists,
                    themeColors,
                    context
                ) {
                    coroutineScope.launch {
                        allEnvironment.toggleTaskStatus(it)
                    }
                }
            }
        }
    }

    @Composable
    fun TodoWidget(
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
        toDoLists.forEach { todo ->
            Text(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(todo.color.toColor()),
                text = todo.name,
                style = widgetTextStyle
            )
            LazyColumn(
                modifier = GlanceModifier.fillMaxSize()
                    .background(themeColorProvider.background)
            ) {
                items(todo.tasks) { item ->
                    Log.d("LOG_TAG---", "TodoListWidget-TodoList#117: ")
                    if (item.status == ToDoStatus.IN_PROGRESS)
                        TodoItem(item, todo.color, widgetTextStyle, context, onClick)
                }
            }
        }
    }
}
