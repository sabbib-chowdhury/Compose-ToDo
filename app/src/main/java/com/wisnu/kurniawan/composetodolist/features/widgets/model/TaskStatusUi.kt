package com.wisnu.kurniawan.composetodolist.features.widgets.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.text.TextDecoration
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.foundation.theme.AlphaDisabled
import com.wisnu.kurniawan.composetodolist.foundation.theme.AlphaHigh
import com.wisnu.kurniawan.composetodolist.foundation.theme.AlphaMedium
import com.wisnu.kurniawan.composetodolist.model.ToDoStatus


sealed interface TaskStatusUi {
    @get:DrawableRes
    val imageRes: Int
    val imageColorFilterAlpha: Float
    val textDecoration: TextDecoration

    @get:Composable
    val taskColor: Color

    @get:Composable
    val taskDetailInfoTextColor: Color

    data object InProgress : TaskStatusUi {
        override val imageRes: Int = R.drawable.ic_widget_round_radio_button_unchecked
        override val imageColorFilterAlpha: Float = AlphaHigh
        override val textDecoration: TextDecoration = TextDecoration.None
        override val taskColor: Color
            @Composable
            get() = GlanceTheme.colors.onSurface.getColor(LocalContext.current)
        override val taskDetailInfoTextColor: Color
            @Composable
            get() = GlanceTheme.colors.onSurface.getColor(LocalContext.current).copy(
                alpha = AlphaMedium
            )
    }

    data object Complete : TaskStatusUi {
        override val imageRes: Int = R.drawable.ic_widget_round_check_circle
        override val imageColorFilterAlpha: Float = AlphaDisabled
        override val textDecoration: TextDecoration = TextDecoration.LineThrough
        override val taskColor: Color
            @Composable
            get() = GlanceTheme.colors.onSurface.getColor(LocalContext.current)
                .copy(alpha = AlphaDisabled)
        override val taskDetailInfoTextColor: Color
            @Composable
            get() = GlanceTheme.colors.onSurface.getColor(LocalContext.current)
                .copy(alpha = AlphaDisabled)
    }

    fun ToDoStatus.toTaskStatusUi(): TaskStatusUi {
        return when (this) {
            ToDoStatus.IN_PROGRESS -> InProgress
            ToDoStatus.COMPLETE -> Complete
        }
    }

}
