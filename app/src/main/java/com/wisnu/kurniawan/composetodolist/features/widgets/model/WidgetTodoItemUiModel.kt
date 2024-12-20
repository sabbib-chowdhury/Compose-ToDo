package com.wisnu.kurniawan.composetodolist.features.widgets.model

import androidx.compose.ui.graphics.Color

data class WidgetTodoItemUiModel(
    val todoListId: String,
    val todoListName: String,
    val todoListColor: Color,
    val taskRowBackgroundColor: Color,
    val todoTasks: List<TaskUiModel>,
)
