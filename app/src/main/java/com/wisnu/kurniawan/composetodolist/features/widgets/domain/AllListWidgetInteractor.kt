package com.wisnu.kurniawan.composetodolist.features.widgets.domain

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.wisnu.kurniawan.composetodolist.features.host.data.HostEnvironment
import com.wisnu.kurniawan.composetodolist.features.todo.all.data.AllEnvironment
import com.wisnu.kurniawan.composetodolist.features.widgets.TodoListWidget
import com.wisnu.kurniawan.composetodolist.model.ToDoTask
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


@EntryPoint
@InstallIn(SingletonComponent::class)
interface AllListWidgetInteractorEntryPoint {
    fun getAllListWidgetInteractor(): AllListWidgetInteractor
}

class AllListWidgetInteractor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val allEnvironment: AllEnvironment,
    hostEnvironment: HostEnvironment,
) {
    val allLists = allEnvironment.getList()
    val themes = hostEnvironment.getTheme()

    internal suspend fun toggleTaskStatus(task: ToDoTask) {
        allEnvironment.toggleTaskStatus(task)
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
