package com.multi.todoappwithexpress.features.tasks.domain.use_case

data class TaskUseCase(
    val createTask: CreateTask,
    val getTasks: GetTasks,
    val getTaskById: GetTaskById,
    val updateTaskById: UpdateTaskById,
    val deleteTaskById: DeleteTaskById
)