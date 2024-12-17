package com.wisnu.kurniawan.composetodolist.features.widgets.settings.model

sealed interface WidgetSettingsUiModel {
    data class SelectableSetting(
        val title: String,
        val isSelected: Boolean
    ) : WidgetSettingsUiModel

    data class RadioSetting(
        val title: String,
        val isSelected: Boolean
    )
}
