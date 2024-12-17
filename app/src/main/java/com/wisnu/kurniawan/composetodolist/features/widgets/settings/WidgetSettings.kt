package com.wisnu.kurniawan.composetodolist.features.widgets.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wisnu.kurniawan.composetodolist.features.widgets.settings.model.WidgetSettingsUiContract
import com.wisnu.kurniawan.composetodolist.features.widgets.settings.model.WidgetSettingsUiModel
import com.wisnu.kurniawan.composetodolist.foundation.theme.DividerAlpha
import com.wisnu.kurniawan.composetodolist.runtime.navigation.WidgetSettings

fun NavGraphBuilder.WidgetSettingsNavHost(
    navHostController: NavHostController,
) {
    navigation(
        startDestination = WidgetSettings.WidgetSettingsScreen.route,
        route = WidgetSettings.Root.route,
    ) {
        Log.d("LOG_TAG---", "-WidgetSettingsNavHost#45: ")
        composable(route = WidgetSettings.WidgetSettingsScreen.route) { backstackEntry ->
            val a = backstackEntry.destination.navigatorName
            val viewModel = hiltViewModel<WidgetSettingsViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            Scaffold(
                topBar = { WidgetSettingsTopBar { navHostController.popBackStack() } }
            ) { contentPadding ->
                Log.d("LOG_TAG---", "-WidgetSettingsNavHost#46: $a $contentPadding")
                WidgetSettingsContent(
                    contentPadding,
                    uiState,
                    onItemSelectionToggle = viewModel::onItemSelectionToggled
                )
            }
        }
    }
}

@Composable
fun WidgetSettingsContent(
    contentPadding: PaddingValues = PaddingValues(vertical = 24.dp),
    uiState: WidgetSettingsUiContract.ViewState,
    onItemSelectionToggle: (WidgetSettingsUiModel.SelectableSetting) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        items(uiState.data) {
            when (it) {
                is WidgetSettingsUiModel.SelectableSetting ->
                    WidgetSettingsCheckedItem(it, onClick = { item -> onItemSelectionToggle(item) })

            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha)
            )
        }
    }
}

@Composable
private fun WidgetSettingsCheckedItem(
    uiState: WidgetSettingsUiModel.SelectableSetting,
    onClick: (WidgetSettingsUiModel.SelectableSetting) -> Unit,
) {
    val icon = if (uiState.isSelected) {
        Icons.Outlined.CheckCircle
    } else {
        Icons.Outlined.RadioButtonUnchecked
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onClick(uiState) }
    ) {
        TextButton(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f),
            onClick = { onClick(uiState) }
        ) {
            Text(
                text = uiState.title,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(0.9f),
                style = MaterialTheme.typography.titleSmall
            )
            Icon(
                imageVector = icon,
                modifier = Modifier
                    .weight(0.1f, fill = false)
                    .padding(vertical = 8.dp),
                contentDescription = uiState.title,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(apiLevel = 35, showBackground = true, showSystemUi = true)
@Composable
private fun WidgetSettingsContentPreview() {
    MaterialTheme {
//        WidgetSettingsContent(uiState = uiState)
    }
}
