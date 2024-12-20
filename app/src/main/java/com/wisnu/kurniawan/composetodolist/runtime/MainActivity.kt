package com.wisnu.kurniawan.composetodolist.runtime

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import androidx.glance.appwidget.updateAll
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.features.host.ui.Host
import com.wisnu.kurniawan.composetodolist.features.widgets.TodoListWidget
import com.wisnu.kurniawan.composetodolist.foundation.window.WindowState
import com.wisnu.kurniawan.composetodolist.foundation.window.rememberWindowState
import com.wisnu.kurniawan.composetodolist.runtime.navigation.ListDetailFlow
import com.wisnu.kurniawan.composetodolist.runtime.navigation.MainNavHost
import com.wisnu.kurniawan.composetodolist.runtime.navigation.WidgetSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var windowState: WindowState

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ComposeToDoList_Light)
        super.onCreate(savedInstanceState)

        val extraString = intent.extras?.getString(this.packageName.plus(WidgetSettings.WidgetSettingsScreen.actionKeySuffix)).orEmpty()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val appEntryPoint = if (extraString == WidgetSettings.WidgetSettingsScreen.name) {
            setOf(WidgetSettings.WidgetSettingsScreen.route)
        } else if (extraString.contains(ListDetailFlow.Root.name)) {
            setOf(
                extraString,
                ListDetailFlow.CreateTask.route,
            )
        } else {
            null
        }

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

