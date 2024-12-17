package com.wisnu.kurniawan.composetodolist.features.widgets.settings

import androidx.lifecycle.ViewModel
import com.wisnu.kurniawan.composetodolist.features.widgets.settings.model.WidgetSettingsUiContract
import com.wisnu.kurniawan.composetodolist.features.widgets.settings.model.WidgetSettingsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class WidgetSettingsViewModel @Inject constructor() : ViewModel()   {

    private val mutableUiState = MutableStateFlow(
        WidgetSettingsUiContract.ViewState(
            listOf(
                WidgetSettingsUiModel.SelectableSetting(
                    "Show Completed Tasks", false
                )
            )
        )
    )
    val uiState = mutableUiState.asStateFlow()

    fun onItemSelectionToggled(item: WidgetSettingsUiModel.SelectableSetting) {
        mutableUiState.update { currentState ->
            currentState.copy(
                data = currentState.data.map {
                    if (it is WidgetSettingsUiModel.SelectableSetting && it.title == item.title) {
                        it.copy(isSelected = !it.isSelected)
                    } else {
                        it
                    }
                }
            )
        }
    }
}
