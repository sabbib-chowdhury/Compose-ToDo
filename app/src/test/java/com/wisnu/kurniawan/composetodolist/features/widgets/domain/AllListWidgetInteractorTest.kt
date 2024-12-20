package com.wisnu.kurniawan.composetodolist.features.widgets.domain

import com.wisnu.kurniawan.composetodolist.expect
import com.wisnu.kurniawan.composetodolist.features.host.data.HostEnvironment
import com.wisnu.kurniawan.composetodolist.features.todo.all.data.AllEnvironment
import com.wisnu.kurniawan.composetodolist.features.widgets.data.AllListWidgetRepository
import com.wisnu.kurniawan.composetodolist.features.widgets.model.AllTodoListWidgetUiContract
import com.wisnu.kurniawan.composetodolist.features.widgets.model.TaskStatusUi
import com.wisnu.kurniawan.composetodolist.features.widgets.model.TaskStatusUi.InProgress.toTaskStatusUi
import com.wisnu.kurniawan.composetodolist.features.widgets.model.TaskUiModel
import com.wisnu.kurniawan.composetodolist.features.widgets.model.WidgetTodoItemUiModel
import com.wisnu.kurniawan.composetodolist.features.widgets.model.WidgetUiModel
import com.wisnu.kurniawan.composetodolist.foundation.extension.toColor
import com.wisnu.kurniawan.composetodolist.model.Theme
import com.wisnu.kurniawan.composetodolist.model.ToDoColor
import com.wisnu.kurniawan.composetodolist.model.ToDoList
import com.wisnu.kurniawan.composetodolist.model.ToDoStatus
import com.wisnu.kurniawan.composetodolist.model.ToDoTask
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
class AllListWidgetInteractorTest {

    private lateinit var interactor: AllListWidgetInteractor
    private val mockAllEnvironment = mockk<AllEnvironment>()
    private val mockHostEnvironment = mockk<HostEnvironment>()
    private val mockAllListWidgetRepository = mockk<AllListWidgetRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        interactor = AllListWidgetInteractor(
            allEnvironment = mockAllEnvironment,
            hostEnvironment = mockHostEnvironment,
            allListWidgetRepository = mockAllListWidgetRepository,
            dispatcher = Dispatchers.Main,
        )

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun givenTodoTasksExistsAndShowCompletedTasksIsTrue_whenLaunches_thenEmitAllTasks() = runTest {
        // given
        val todoLists = listOf(
            ToDoList(
                id = "1",
                name = "List 1",
                color = ToDoColor.RED,
                tasks = listOf(
                    ToDoTask(
                        id = "2",
                        name = "Task 2",
                        status = ToDoStatus.IN_PROGRESS,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    ),
                    ToDoTask(
                        id = "1",
                        name = "Task 1",
                        status = ToDoStatus.COMPLETE,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    ),
                ),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )
        )
        val showCompletedTasks = true
        coEvery { mockAllEnvironment.getList() } returns flowOf(todoLists)
        coEvery { mockAllListWidgetRepository.getWidgetSettings() } returns flowOf(
            showCompletedTasks
        )

        // when
        interactor.allLists.expect(
            WidgetUiModel(
                data = listOf(
                    WidgetTodoItemUiModel(
                        todoListId = todoLists[0].id,
                        todoTasks = todoLists[0].tasks.map {
                            TaskUiModel(
                                it.id,
                                it.name,
                                it,
                                it.status.toTaskStatusUi(),
                            )
                        },
                        todoListColor = todoLists[0].color.toColor(),
                        todoListName = todoLists[0].name,
                        taskRowBackgroundColor = todoLists[0].color.toColor()
                    )
                ),
            )
        )
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun givenTodoTasksExistsAndShowCompletedTasksIsFalse_whenLaunches_thenEmitInProgressTasksOnly() =
        runTest {
            // given
            val localDateTime = LocalDateTime.now()
            val todoLists = listOf(
                ToDoList(
                    id = "1",
                    name = "List 1",
                    color = ToDoColor.RED,
                    tasks = listOf(
                        ToDoTask(
                            id = "2",
                            name = "Task 2",
                            status = ToDoStatus.IN_PROGRESS,
                            createdAt = localDateTime,
                            updatedAt = localDateTime
                        ),
                        ToDoTask(
                            id = "1",
                            name = "Task 1",
                            status = ToDoStatus.COMPLETE,
                            createdAt = localDateTime,
                            updatedAt = localDateTime
                        ),
                    ),
                    createdAt = localDateTime,
                    updatedAt = localDateTime,
                )
            )
            val showCompletedTasks = false
            coEvery { mockAllEnvironment.getList() } returns flowOf(todoLists)
            coEvery { mockAllListWidgetRepository.getWidgetSettings() } returns flowOf(
                showCompletedTasks
            )

            // when
            interactor.allLists.expect(
                WidgetUiModel(
                    data = listOf(
                        WidgetTodoItemUiModel(
                            todoListId = todoLists[0].id,
                            todoTasks = listOf(
                                TaskUiModel(
                                    "2",
                                    "Task 2",
                                    ToDoTask(
                                        id = "2",
                                        name = "Task 2",
                                        status = ToDoStatus.IN_PROGRESS,
                                        createdAt = localDateTime,
                                        updatedAt = localDateTime
                                    ),
                                    TaskStatusUi.InProgress,
                                )
                            ),
                            todoListColor = todoLists[0].color.toColor(),
                            todoListName = todoLists[0].name,
                            taskRowBackgroundColor = todoLists[0].color.toColor()
                        )
                    ),
                )
            )
        }

    @Test
    fun givenTasksInProgress_whenToggleTaskStatus_thenTaskIsComplete() = runTest {
        // given
        val localDateTime = LocalDateTime.now()
        val mockTask = ToDoTask(
            id = "2",
            name = "Task 2",
            status = ToDoStatus.IN_PROGRESS,
            createdAt = localDateTime,
            updatedAt = localDateTime
        )
        coEvery { mockAllEnvironment.toggleTaskStatus(mockTask) } returns Unit

        // when
        interactor.toggleTaskStatus(mockTask)

        // then
        coVerify { mockAllEnvironment.toggleTaskStatus(mockTask) }
        val replayCache = interactor.actionState.replayCache
        assertTrue(replayCache[0] is AllTodoListWidgetUiContract.Actions.UpdateWidget)
    }

    @Test
    fun givenTasksIsComplete_whenToggleTaskStatus_thenTaskInProgress() = runTest {
        // given
        val localDateTime = LocalDateTime.now()
        val mockTask = ToDoTask(
            id = "2",
            name = "Task 2",
            status = ToDoStatus.COMPLETE,
            createdAt = localDateTime,
            updatedAt = localDateTime
        )
        coEvery { mockAllEnvironment.toggleTaskStatus(mockTask) } returns Unit

        // when
        interactor.toggleTaskStatus(mockTask)

        // then
        coVerify { mockAllEnvironment.toggleTaskStatus(mockTask) }
        val replayCache = interactor.actionState.replayCache
        assertTrue(replayCache[0] is AllTodoListWidgetUiContract.Actions.UpdateWidget)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun givenThemesInHostEnvironment_whenThemeChanges_thenUpdateWidgets() = runTest {
        // given
        every { mockHostEnvironment.getTheme() } returns flowOf(
            Theme.LIGHT,
            Theme.TWILIGHT,
            Theme.AURORA,
        )
        // when
        val themes = interactor.themes
        val actionReplayCache = interactor.actionState.replayCache

        // then
        themes.expect(Theme.LIGHT)
        verify { mockHostEnvironment.getTheme() }
        assertTrue(
            actionReplayCache.containsAll(
                listOf(AllTodoListWidgetUiContract.Actions.UpdateWidget)
            )
        )
    }

    @Test
    fun givenShowCompletedTasksIsFalse_whenUpdateWidgetSettings_thenShowCompletedTasks() = runTest {
        // given
        coEvery { mockAllListWidgetRepository.setWidgetSettings(true) } returns Unit

        // when
        interactor.setWidgetSettings(true)

        // then
        coVerify { mockAllListWidgetRepository.setWidgetSettings(true) }
        interactor.actionState.replayCache.containsAll(
            listOf(AllTodoListWidgetUiContract.Actions.UpdateWidget)
        ).let {
            assertTrue(it)
        }
    }
}
