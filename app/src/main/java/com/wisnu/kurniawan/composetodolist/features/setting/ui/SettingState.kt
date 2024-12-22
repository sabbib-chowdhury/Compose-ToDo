package com.wisnu.kurniawan.composetodolist.features.setting.ui

import androidx.compose.runtime.Immutable
import com.wisnu.kurniawan.composetodolist.R

@Immutable
data class SettingState(
    val items: List<SettingItem> = initial()
) {
    companion object {
        private fun initial() = listOf(
            SettingItem.Theme(R.string.setting_theme),
            SettingItem.Language(R.string.setting_language),
            SettingItem.WidgetSettings(R.string.setting_widget_settings),
            // SettingItem.Logout(R.string.setting_logout),
        )
    }
}

sealed class SettingItem(open val title: Int) {
    data class Theme(override val title: Int) : SettingItem(title)
    data class Logout(override val title: Int) : SettingItem(title)
    data class Language(override val title: Int) : SettingItem(title)
    data class WidgetSettings(override val title: Int) : SettingItem(title)
}
