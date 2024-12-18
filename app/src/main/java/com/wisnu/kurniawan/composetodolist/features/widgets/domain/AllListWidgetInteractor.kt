package com.wisnu.kurniawan.composetodolist.features.widgets.domain

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.wisnu.kurniawan.composetodolist.features.host.data.HostEnvironment
import com.wisnu.kurniawan.composetodolist.features.todo.all.data.AllEnvironment
import com.wisnu.kurniawan.composetodolist.features.widgets.TodoListWidget
import com.wisnu.kurniawan.composetodolist.features.widgets.data.AllListWidgetRepository
import com.wisnu.kurniawan.composetodolist.model.Theme
import com.wisnu.kurniawan.composetodolist.model.ToDoStatus
import com.wisnu.kurniawan.composetodolist.model.ToDoTask
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@EntryPoint
@InstallIn(SingletonComponent::class)
interface AllListWidgetInteractorEntryPoint {
    fun getAllListWidgetInteractor(): AllListWidgetInteractor
}

class AllListWidgetInteractor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val allEnvironment: AllEnvironment,
    private val hostEnvironment: HostEnvironment,
    private val allListWidgetRepository: AllListWidgetRepository,
) {

    val allLists = combine(
        allEnvironment.getList(),
        allListWidgetRepository.getWidgetSettings()
    ) { todoLists, showCompletedTasks ->
        if (showCompletedTasks) {
            todoLists.map {
                it.copy(tasks = it.tasks.sortedBy { task -> task.status })
            }
        } else {
            todoLists.map {
                it.copy(tasks = it.tasks.filter { task -> task.status == ToDoStatus.IN_PROGRESS })
            }
        }
    }

    val themes: Flow<Theme>
        get() {
            return hostEnvironment.getTheme()
                .onEach {
                    TodoListWidget().updateAll(context)
                }
        }

    internal suspend fun toggleTaskStatus(task: ToDoTask) {
        allEnvironment.toggleTaskStatus(task)
        TodoListWidget().updateAll(context)
    }

    fun fetchWidgetSettings(): Flow<Boolean> {
        return allListWidgetRepository.getWidgetSettings()
    }

    suspend fun setWidgetSettings(showCompletedTasks: Boolean) {
        allListWidgetRepository.setWidgetSettings(showCompletedTasks)
        TodoListWidget().updateAll(context)
    }

    companion object {
        fun get(context: Context): AllListWidgetInteractor =
            EntryPointAccessors.fromApplication(
                context.applicationContext,
                AllListWidgetInteractorEntryPoint::class.java
            ).getAllListWidgetInteractor()
    }
}
