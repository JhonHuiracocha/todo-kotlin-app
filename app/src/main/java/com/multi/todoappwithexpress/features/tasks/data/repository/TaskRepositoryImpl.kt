package com.multi.todoappwithexpress.features.tasks.data.repository

import com.multi.todoappwithexpress.features.tasks.data.source.remote.TaskApi
import com.multi.todoappwithexpress.features.tasks.data.source.remote.dto.ApiResponse
import com.multi.todoappwithexpress.features.tasks.data.source.remote.dto.Task
import com.multi.todoappwithexpress.features.tasks.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskApi: TaskApi
) : TaskRepository {
    override fun getTasks(): Flow<ApiResponse<List<Task>>> = flow {
        emit(taskApi.getTasks())
    }

    override fun getTaskById(taskId: Int): Flow<ApiResponse<Task?>> = flow {
        emit(taskApi.getTaskById(taskId))
    }

    override fun createTask(task: Task): Flow<ApiResponse<Task>> = flow {
        emit(taskApi.createTask(task))
    }

    override fun updateTaskById(taskId: Int, task: Task): Flow<ApiResponse<Nothing>> = flow {
        emit(taskApi.updateTaskById(taskId, task))
    }

    override fun deleteTaskById(taskId: Int): Flow<ApiResponse<Nothing>> = flow {
        emit(taskApi.deleteTaskById(taskId))
    }
}