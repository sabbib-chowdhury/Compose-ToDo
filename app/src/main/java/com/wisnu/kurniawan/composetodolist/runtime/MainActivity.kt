package com.wisnu.kurniawan.composetodolist.runtime

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.features.host.ui.Host
import com.wisnu.kurniawan.composetodolist.foundation.window.WindowState
import com.wisnu.kurniawan.composetodolist.foundation.window.rememberWindowState
import com.wisnu.kurniawan.composetodolist.model.AppEntryPoint
import com.wisnu.kurniawan.composetodolist.runtime.navigation.MainNavHost
import com.wisnu.kurniawan.composetodolist.runtime.navigation.WidgetSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var windowState: WindowState

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ComposeToDoList_Light)
        super.onCreate(savedInstanceState)

        val extraString = intent.extras?.getString(this.packageName.plus(WidgetSettings.WidgetSettingsScreen.actionKeySuffix))
        val appEntryPoint = if (extraString == WidgetSettings.WidgetSettingsScreen.actionValue) {
            AppEntryPoint.WidgetSettingsEntryPoint
        } else null
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            windowState = rememberWindowState()

            Host {
                Surface {
                    MainNavHost(windowState, appEntryPoint)
                }
            }
        }
    }
}

