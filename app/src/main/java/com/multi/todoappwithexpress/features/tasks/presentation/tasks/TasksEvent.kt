package com.multi.todoappwithexpress.features.tasks.presentation.tasks

sealed class TasksEvent {
    data class DeleteNote(val taskId: Int) : TasksEvent()
}
