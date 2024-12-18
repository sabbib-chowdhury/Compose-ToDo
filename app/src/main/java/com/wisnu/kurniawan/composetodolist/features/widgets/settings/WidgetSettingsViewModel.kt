package com.wisnu.kurniawan.composetodolist.features.widgets.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wisnu.kurniawan.composetodolist.features.widgets.domain.AllListWidgetInteractor
import com.wisnu.kurniawan.composetodolist.features.widgets.settings.model.WidgetSettingsUiContract
import com.wisnu.kurniawan.composetodolist.features.widgets.settings.model.WidgetSettingsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WidgetSettingsViewModel @Inject constructor(
    private val allListWidgetInteractor: AllListWidgetInteractor,
) : ViewModel() {

    private val initialValue = WidgetSettingsUiContract.ViewState(
        data = listOf(
            WidgetSettingsUiModel.SelectableSetting(
                title = "Show Completed Tasks",
                isSelected = false
            )
        )
    )
    private val mutableUiState = MutableStateFlow(initialValue)
    val uiState = mutableUiState.asStateFlow()

    init {
        viewModelScope.launch {
            allListWidgetInteractor.fetchWidgetSettings().collect {
                mutableUiState.value = initialValue.copy(
                    data = initialValue.data.map { uiState ->
                        when (uiState) {
                            is WidgetSettingsUiModel.SelectableSetting -> uiState.copy(isSelected = it)
                        }
                    }
                )
            }
        }
    }

    fun onItemSelectionToggled(item: WidgetSettingsUiModel.SelectableSetting) {
        viewModelScope.launch {
            allListWidgetInteractor.setWidgetSettings(!item.isSelected)
        }
    }
}
