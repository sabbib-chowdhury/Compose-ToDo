package com.wisnu.kurniawan.composetodolist.features.host.data

import android.content.Context
import com.wisnu.kurniawan.composetodolist.features.widgets.di.WidgetDependencies
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.provider.ThemeProvider
import com.wisnu.kurniawan.composetodolist.model.Theme
import dagger.hilt.EntryPoints
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HostEnvironment @Inject constructor(
    private val themeProvider: ThemeProvider
) : IHostEnvironment {

    override fun getTheme(): Flow<Theme> {
        return themeProvider.getTheme()
    }

    companion object {
        fun get(context: Context): HostEnvironment {
            return EntryPoints.get(
                context.applicationContext,
                WidgetDependencies::class.java
            ).getHostEnvironment()
        }
    }
}
