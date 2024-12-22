package com.wisnu.kurniawan.composetodolist.features.widgets.model

sealed interface AllTodoListWidgetUiContract {
    data class WidgetUiModel(
        val data: List<WidgetTodoItemUiModel>,
    )

    sealed interface Actions {
        data object UpdateWidget : Actions
    }
}
