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
import androidx.glance.color.colorProviders
import androidx.glance.unit.ColorProvider
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
fun ColorScheme.toColorProviders(context: Context): ColorProviders = if (context.isDarkMode) {
    colorProviders(
        primary = ColorProvider(NightColorPalette.primary),
        onPrimary = ColorProvider(NightColorPalette.onPrimary),
        primaryContainer = ColorProvider(NightColorPalette.primaryContainer),
        onPrimaryContainer = ColorProvider(NightColorPalette.onPrimaryContainer),
        secondary = ColorProvider(NightColorPalette.secondary),
        onSecondary = ColorProvider(NightColorPalette.onSecondary),
        secondaryContainer = ColorProvider(NightColorPalette.secondaryContainer),
        onSecondaryContainer = ColorProvider(NightColorPalette.onSecondaryContainer),
        tertiary = ColorProvider(NightColorPalette.tertiary),
        onTertiary = ColorProvider(NightColorPalette.onTertiary),
        tertiaryContainer = ColorProvider(NightColorPalette.tertiaryContainer),
        onTertiaryContainer = ColorProvider(NightColorPalette.onTertiaryContainer),
        error = ColorProvider(NightColorPalette.error),
        errorContainer = ColorProvider(NightColorPalette.errorContainer),
        onError = ColorProvider(NightColorPalette.onError),
        onErrorContainer = ColorProvider(NightColorPalette.onErrorContainer),
        background = ColorProvider(NightColorPalette.background),
        onBackground = ColorProvider(NightColorPalette.onBackground),
        surface = ColorProvider(NightColorPalette.surface),
        onSurface = ColorProvider(NightColorPalette.onSurface),
        surfaceVariant = ColorProvider(NightColorPalette.surfaceVariant),
        onSurfaceVariant = ColorProvider(NightColorPalette.onSurfaceVariant),
        outline = ColorProvider(NightColorPalette.outline),
        inverseOnSurface = ColorProvider(NightColorPalette.inverseOnSurface),
        inverseSurface = ColorProvider(NightColorPalette.inverseSurface),
        inversePrimary = ColorProvider(NightColorPalette.inversePrimary),
    )
} else {
    colorProviders(
        primary = ColorProvider(LightColorPalette.primary),
        onPrimary = ColorProvider(LightColorPalette.onPrimary),
        primaryContainer = ColorProvider(LightColorPalette.primaryContainer),
        onPrimaryContainer = ColorProvider(LightColorPalette.onPrimaryContainer),
        secondary = ColorProvider(LightColorPalette.secondary),
        onSecondary = ColorProvider(LightColorPalette.onSecondary),
        secondaryContainer = ColorProvider(LightColorPalette.secondaryContainer),
        onSecondaryContainer = ColorProvider(LightColorPalette.onSecondaryContainer),
        tertiary = ColorProvider(LightColorPalette.tertiary),
        onTertiary = ColorProvider(LightColorPalette.onTertiary),
        tertiaryContainer = ColorProvider(LightColorPalette.tertiaryContainer),
        onTertiaryContainer = ColorProvider(LightColorPalette.onTertiaryContainer),
        error = ColorProvider(LightColorPalette.error),
        errorContainer = ColorProvider(LightColorPalette.errorContainer),
        onError = ColorProvider(LightColorPalette.onError),
        onErrorContainer = ColorProvider(LightColorPalette.onErrorContainer),
        background = ColorProvider(LightColorPalette.background),
        onBackground = ColorProvider(LightColorPalette.onBackground),
        surface = ColorProvider(LightColorPalette.surface),
        onSurface = ColorProvider(LightColorPalette.onSurface),
        surfaceVariant = ColorProvider(LightColorPalette.surfaceVariant),
        onSurfaceVariant = ColorProvider(LightColorPalette.onSurfaceVariant),
        outline = ColorProvider(LightColorPalette.outline),
        inverseOnSurface = ColorProvider(LightColorPalette.inverseOnSurface),
        inverseSurface = ColorProvider(LightColorPalette.inverseSurface),
        inversePrimary = ColorProvider(LightColorPalette.inversePrimary),
    )
}

val Context.isDarkMode: Boolean
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    get() = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_YES

