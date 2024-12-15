package com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.mapper

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RestrictTo
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.glance.LocalContext as GlanceLocalContext
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
fun Theme.toGlanceColorProviders(): ColorProviders {
    val themeColors = when(this) {
        Theme.SYSTEM -> {
            if (GlanceLocalContext.current.isDarkMode) {
                NightColorPalette
            } else {
                LightColorPalette
            }
        }
        Theme.WALLPAPER -> {
            if (GlanceLocalContext.current.isDarkMode) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    dynamicDarkColorScheme(GlanceLocalContext.current)
                } else {
                    NightColorPalette
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    dynamicLightColorScheme(GlanceLocalContext.current)
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
    return ColorProviders(themeColors)
}

val Context.isDarkMode: Boolean
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    get() = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_YES

