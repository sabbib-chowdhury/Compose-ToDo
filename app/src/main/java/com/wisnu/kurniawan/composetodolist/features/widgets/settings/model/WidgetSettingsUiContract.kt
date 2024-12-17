package com.wisnu.kurniawan.composetodolist.features.widgets.settings.model

sealed interface WidgetSettingsUiContract {
    data class ViewState(
        val data: List<WidgetSettingsUiModel>
    ) : WidgetSettingsUiContract
}
