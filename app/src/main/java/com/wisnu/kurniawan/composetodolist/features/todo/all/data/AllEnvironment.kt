package com.wisnu.kurniawan.composetodolist.features.todo.all.data

import android.content.Context
import android.util.Log
import com.wisnu.kurniawan.composetodolist.features.widgets.di.WidgetDependencies
import com.wisnu.kurniawan.composetodolist.foundation.datasource.local.provider.ToDoListProvider
import com.wisnu.kurniawan.composetodolist.foundation.datasource.local.provider.ToDoTaskProvider
import com.wisnu.kurniawan.composetodolist.foundation.extension.toggleStatusHandler
import com.wisnu.kurniawan.composetodolist.foundation.wrapper.DateTimeProvider
import com.wisnu.kurniawan.composetodolist.model.ToDoList
import com.wisnu.kurniawan.composetodolist.model.ToDoTask
import dagger.hilt.EntryPoints
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllEnvironment @Inject constructor(
    override val dateTimeProvider: DateTimeProvider,
    private val toDoListProvider: ToDoListProvider,
    private val toDoTaskProvider: ToDoTaskProvider
) : IAllEnvironment {

    override fun getList(): Flow<List<ToDoList>> {
        return toDoListProvider.getListWithTasks()
    }

    override suspend fun toggleTaskStatus(toDoTask: ToDoTask) {
        Log.d("LOG_TAG---", "AllEnvironment-toggleTaskStatus#28: $toDoTask")
        val currentDate = dateTimeProvider.now()
        toDoTask.toggleStatusHandler(
            currentDate,
            { completedAt, newStatus ->
                toDoTaskProvider.updateTaskStatus(toDoTask.id, newStatus, completedAt, currentDate)
            },
            { nextDueDate ->
                toDoTaskProvider.updateTaskDueDate(toDoTask.id, nextDueDate, toDoTask.isDueDateTimeSet, currentDate)
            }
        )
    }

    override suspend fun deleteTask(task: ToDoTask) {
        toDoTaskProvider.deleteTaskById(task.id)
    }

    companion object {
        fun get(context: Context): AllEnvironment {
            return EntryPoints.get(
                context.applicationContext,
                WidgetDependencies::class.java
            ).getAllEnvironment()
        }
    }
}
