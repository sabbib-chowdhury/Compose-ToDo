package com.wisnu.kurniawan.composetodolist.features.widgets.model

import com.wisnu.kurniawan.composetodolist.model.ToDoTask

data class TaskUiModel(
    val taskId: String,
    val taskName: String,
    val taskDetailInfo: ToDoTask,
    val taskStatus: TaskStatusUi,
)
