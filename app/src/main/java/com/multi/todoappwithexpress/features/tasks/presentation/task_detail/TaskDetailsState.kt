package com.multi.todoappwithexpress.features.tasks.presentation.task_detail

data class TaskDetailsState(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val message: String? = null
)
