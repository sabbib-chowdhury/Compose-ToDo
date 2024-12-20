package com.wisnu.kurniawan.composetodolist.features.widgets.model

sealed interface AllTodoListWidgetUiContract {
    sealed interface Actions {
        data object UpdateWidget : Actions
    }
}
