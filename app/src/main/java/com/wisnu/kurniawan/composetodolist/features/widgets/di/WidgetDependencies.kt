package com.wisnu.kurniawan.composetodolist.features.widgets.di

import com.wisnu.kurniawan.composetodolist.features.host.data.HostEnvironment
import com.wisnu.kurniawan.composetodolist.features.todo.all.data.AllEnvironment
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetDependencies {
    fun getHostEnvironment(): HostEnvironment
    fun getAllEnvironment(): AllEnvironment
}
