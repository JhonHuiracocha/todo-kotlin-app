package com.multi.todoappwithexpress.features.tasks.presentation.tasks

import com.multi.todoappwithexpress.features.tasks.data.source.remote.dto.Task

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val message: String? = null,
    val isLoading: Boolean = false
)
