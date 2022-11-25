package com.multi.todoappwithexpress.features.tasks.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multi.todoappwithexpress.features.tasks.domain.use_case.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TasksState())
    val state = _state.asStateFlow()

    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.DeleteNote -> {
                deleteTaskById(event.taskId)
            }
        }
    }

    fun getTasks() {
        viewModelScope.launch {
            taskUseCase.getTasks()
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
                            tasks = res.data ?: emptyList(),
                            message = res.message,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun deleteTaskById(taskId: Int) {
        viewModelScope.launch {
            taskUseCase.deleteTaskById(taskId)
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
                            tasks = it.tasks.filter { task ->
                                task.id != taskId
                            }
                        )
                    }
                }
        }
    }
}