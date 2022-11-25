package com.multi.todoappwithexpress.features.tasks.presentation.task_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multi.todoappwithexpress.features.tasks.data.source.remote.dto.Task
import com.multi.todoappwithexpress.features.tasks.domain.use_case.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskUseCase: TaskUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TaskDetailsState())
    val state = _state.asStateFlow()

    val taskId: Int? = savedStateHandle.get<Int>("taskId")

    init {
        taskId?.also { taskId ->
            getTaskById(taskId)
        }
    }

    fun onEvent(event: TaskDetailsEvent) {
        when (event) {
            is TaskDetailsEvent.EnteredTitle -> {
                _state.update {
                    it.copy(
                        title = event.value
                    )
                }
            }
            is TaskDetailsEvent.EnteredDescription -> {
                _state.update {
                    it.copy(
                        description = event.value
                    )
                }
            }
            is TaskDetailsEvent.Submit -> {
                if (taskId == -1) {
                    createTask()
                } else {
                    updateTaskById()
                }
            }
            is TaskDetailsEvent.DeleteTaskById -> {
                deleteTaskById()
            }
        }
    }

    private fun getTaskById(taskId: Int) {
        viewModelScope.launch {
            taskUseCase.getTaskById(taskId)
                .flowOn(Dispatchers.IO)
                .onStart {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = e.message
                        )
                    }
                }
                .collect { res ->
                    _state.update {
                        it.copy(
                            id = res.data?.id ?: -1,
                            title = res.data?.title ?: "",
                            description = res.data?.description ?: "",
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun createTask() {
        viewModelScope.launch {
            val task = Task(
                id = Random.nextInt(),
                title = state.value.title,
                description = state.value.description
            )

            taskUseCase.createTask(task)
                .flowOn(Dispatchers.IO)
                .onStart {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = e.message
                        )
                    }
                }
                .collect { res ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = res.message
                        )
                    }
                }
        }
    }

    private fun updateTaskById() {
        viewModelScope.launch {
            taskId?.let {
                val task = Task(
                    id = taskId,
                    title = state.value.title,
                    description = state.value.description
                )

                taskUseCase.updateTaskById(taskId, task)
                    .flowOn(Dispatchers.IO)
                    .onStart {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    .catch { e ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                message = e.message
                            )
                        }
                    }
                    .collect { res ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                message = res.message
                            )
                        }
                    }
            }
        }
    }

    fun deleteTaskById() {
        viewModelScope.launch {
            taskId?.let { id ->
                taskUseCase.deleteTaskById(id)
                    .flowOn(Dispatchers.IO)
                    .onStart {
                        _state.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }
                    .catch { e ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                message = e.message
                            )
                        }
                    }
                    .collect { res ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                message = res.message,
                            )
                        }
                    }
            }
        }
    }
}