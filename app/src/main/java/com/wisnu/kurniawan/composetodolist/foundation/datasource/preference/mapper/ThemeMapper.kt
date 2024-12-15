package com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.mapper

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RestrictTo
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.glance.color.ColorProviders
import androidx.glance.material3.ColorProviders
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.model.ThemePreference
import com.wisnu.kurniawan.composetodolist.foundation.theme.AuroraColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.LightColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.NightColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.SunriseColorPalette
import com.wisnu.kurniawan.composetodolist.foundation.theme.TwilightColorPalette
import com.wisnu.kurniawan.composetodolist.model.Theme

fun Theme.toThemePreference() = when (this) {
    Theme.SYSTEM -> ThemePreference.SYSTEM
    Theme.LIGHT -> ThemePreference.LIGHT
    Theme.TWILIGHT -> ThemePreference.TWILIGHT
    Theme.NIGHT -> ThemePreference.NIGHT
    Theme.SUNRISE -> ThemePreference.SUNRISE
    Theme.AURORA -> ThemePreference.AURORA
    Theme.WALLPAPER -> ThemePreference.WALLPAPER
}

fun ThemePreference.toTheme() = when (this) {
    ThemePreference.SYSTEM -> Theme.SYSTEM
    ThemePreference.LIGHT -> Theme.LIGHT
    ThemePreference.TWILIGHT -> Theme.TWILIGHT
    ThemePreference.NIGHT -> Theme.NIGHT
    ThemePreference.SUNRISE -> Theme.SUNRISE
    ThemePreference.AURORA -> Theme.AURORA
    ThemePreference.WALLPAPER -> Theme.WALLPAPER
    ThemePreference.UNRECOGNIZED -> Theme.SYSTEM
}

@Composable
fun Theme.toColorScheme(context: Context) = when(this) {
    Theme.SYSTEM -> {
        if (context.isDarkMode) {
            NightColorPalette
        } else {
            LightColorPalette
        }
    }
    Theme.WALLPAPER -> {
        if (context.isDarkMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                dynamicDarkColorScheme(LocalContext.current)
            } else {
                NightColorPalette
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                dynamicLightColorScheme(LocalContext.current)
            } else {
                LightColorPalette
            }
        }
    }
    Theme.LIGHT -> LightColorPalette
    Theme.TWILIGHT -> TwilightColorPalette
    Theme.NIGHT -> NightColorPalette
    Theme.SUNRISE -> SunriseColorPalette
    Theme.AURORA -> AuroraColorPalette
}

@Composable
fun ColorScheme.toColorProviders(): ColorProviders =
    ColorProviders(LightColorPalette, NightColorPalette)

val Context.isDarkMode: Boolean
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    get() = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_YES

