package com.multi.todoappwithexpress.features.tasks.domain.use_case

import com.multi.todoappwithexpress.features.tasks.data.source.remote.dto.ApiResponse
import com.multi.todoappwithexpress.features.tasks.data.source.remote.dto.Task
import com.multi.todoappwithexpress.features.tasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasks(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<ApiResponse<List<Task>>> {
        return taskRepository.getTasks()
    }
}