package com.wisnu.kurniawan.composetodolist.model

import com.wisnu.kurniawan.composetodolist.runtime.navigation.WidgetSettings

enum class AppEntryPoint(val route: String) {
    WidgetSettingsEntryPoint(WidgetSettings.WidgetSettingsScreen.route)
}
