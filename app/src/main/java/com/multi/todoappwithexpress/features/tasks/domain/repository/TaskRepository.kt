package com.multi.todoappwithexpress.features.tasks.domain.repository

import com.multi.todoappwithexpress.features.tasks.data.source.remote.dto.Task
import com.multi.todoappwithexpress.features.tasks.data.source.remote.dto.ApiResponse
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<ApiResponse<List<Task>>>
    fun getTaskById(taskId: Int): Flow<ApiResponse<Task?>>
    fun createTask(task: Task): Flow<ApiResponse<Task>>
    fun updateTaskById(taskId: Int, task: Task): Flow<ApiResponse<Nothing>>
    fun deleteTaskById(taskId: Int): Flow<ApiResponse<Nothing>>
}