package com.wisnu.kurniawan.composetodolist.features.widgets.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllListWidgetRepository @Inject constructor(
    private val allListWidgetDataSource: AllListWidgetDataSource,
) {
    fun getWidgetSettings(): Flow<Boolean> {
        return allListWidgetDataSource.getWidgetSettings()
    }

    suspend fun setWidgetSettings(showCompletedTasks: Boolean) {
        allListWidgetDataSource.setWidgetSettings(showCompletedTasks)
    }
}
