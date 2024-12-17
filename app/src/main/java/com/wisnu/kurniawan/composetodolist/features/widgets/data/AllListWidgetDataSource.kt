package com.wisnu.kurniawan.composetodolist.features.widgets.data

import androidx.datastore.core.DataStore
import com.wisnu.kurniawan.composetodolist.foundation.datasource.preference.model.UserWidgetSettingsPreference
import com.wisnu.kurniawan.composetodolist.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class AllListWidgetDataSource @Inject constructor(
    @Named(DiName.DISPATCHER_IO) private val dispatcher: CoroutineDispatcher,
    private val widgetSettingsDataStore: DataStore<UserWidgetSettingsPreference>,
) {

    // TODO: Consider a repo model
    fun getWidgetSettings(): Flow<Boolean> {
        return widgetSettingsDataStore.data
            .map {
                it.showCompletedTasks
            }
            .catch {
                emit(false)
            }
            .flowOn(dispatcher)
    }

    suspend fun setWidgetSettings(showCompletedTasks: Boolean) {
        withContext(dispatcher) {
            widgetSettingsDataStore.updateData {
                UserWidgetSettingsPreference
                    .newBuilder()
                    .setShowCompletedTasks(showCompletedTasks)
                    .build()
            }
        }
    }
}
