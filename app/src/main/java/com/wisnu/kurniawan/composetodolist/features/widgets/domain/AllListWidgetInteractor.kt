package com.wisnu.kurniawan.composetodolist.features.widgets.domain

import android.content.Context
import com.wisnu.kurniawan.composetodolist.features.host.data.HostEnvironment
import com.wisnu.kurniawan.composetodolist.features.todo.all.data.AllEnvironment
import com.wisnu.kurniawan.composetodolist.features.widgets.data.AllListWidgetRepository
import com.wisnu.kurniawan.composetodolist.features.widgets.model.AllTodoListWidgetUiContract
import com.wisnu.kurniawan.composetodolist.features.widgets.model.TaskStatusUi.InProgress.toTaskStatusUi
import com.wisnu.kurniawan.composetodolist.features.widgets.model.TaskUiModel
import com.wisnu.kurniawan.composetodolist.features.widgets.model.WidgetTodoItemUiModel
import com.wisnu.kurniawan.composetodolist.features.widgets.model.WidgetUiModel
import com.wisnu.kurniawan.composetodolist.foundation.di.DiName
import com.wisnu.kurniawan.composetodolist.foundation.extension.toColor
import com.wisnu.kurniawan.composetodolist.model.Theme
import com.wisnu.kurniawan.composetodolist.model.ToDoStatus
import com.wisnu.kurniawan.composetodolist.model.ToDoTask
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named


@EntryPoint
@InstallIn(SingletonComponent::class)
interface AllListWidgetInteractorEntryPoint {
    fun getAllListWidgetInteractor(): AllListWidgetInteractor
}

open class AllListWidgetInteractor @Inject constructor(
    private val allEnvironment: AllEnvironment,
    private val hostEnvironment: HostEnvironment,
    private val allListWidgetRepository: AllListWidgetRepository,
    @Named(DiName.DISPATCHER_IO)
    private val dispatcher: CoroutineDispatcher
) {

    private val mutableActionState = MutableStateFlow(AllTodoListWidgetUiContract.Actions.UpdateWidget)
    val actionState: StateFlow<AllTodoListWidgetUiContract.Actions> = mutableActionState.asStateFlow()

    val allLists: StateFlow<WidgetUiModel>
        get() = allEnvironment.getList().combine(
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
            }.map { doList ->
                WidgetTodoItemUiModel(
                    todoListId = doList.id,
                    todoListName = doList.name,
                    todoListColor = doList.color.toColor(),
                    taskRowBackgroundColor = doList.color.toColor(),
                    todoTasks = doList.tasks.map { task ->
                        TaskUiModel(
                            taskId = task.id,
                            taskName = task.name,
                            taskDetailInfo = task,
                            taskStatus = task.status.toTaskStatusUi()
                        )
                    }
                )
            }
                .let {
                    WidgetUiModel(it)
                }
        }.stateIn(
            CoroutineScope(Dispatchers.Main),
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            WidgetUiModel(emptyList())
        )

    val themes: Flow<Theme>
        get() {
            return hostEnvironment.getTheme()
                .onEach {
                    mutableActionState.tryEmit(AllTodoListWidgetUiContract.Actions.UpdateWidget)
                }
        }

    internal suspend fun toggleTaskStatus(task: ToDoTask) {
        withContext(context = dispatcher) {
            allEnvironment.toggleTaskStatus(task)
            mutableActionState.tryEmit(AllTodoListWidgetUiContract.Actions.UpdateWidget)
        }
    }

    fun fetchWidgetSettings(): Flow<Boolean> {
        return allListWidgetRepository.getWidgetSettings()
    }

    suspend fun setWidgetSettings(showCompletedTasks: Boolean) {
        allListWidgetRepository.setWidgetSettings(showCompletedTasks)
        mutableActionState.tryEmit(AllTodoListWidgetUiContract.Actions.UpdateWidget)
    }

    companion object {
        fun get(context: Context): AllListWidgetInteractor =
            EntryPointAccessors.fromApplication(
                context.applicationContext,
                AllListWidgetInteractorEntryPoint::class.java
            ).getAllListWidgetInteractor()
    }
}
