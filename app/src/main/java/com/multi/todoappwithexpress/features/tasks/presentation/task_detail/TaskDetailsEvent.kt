package com.multi.todoappwithexpress.features.tasks.presentation.task_detail

sealed class TaskDetailsEvent {
    data class EnteredTitle(val value: String) : TaskDetailsEvent()
    data class EnteredDescription(val value: String) : TaskDetailsEvent()
    object DeleteTaskById: TaskDetailsEvent()
    object Submit: TaskDetailsEvent()
}