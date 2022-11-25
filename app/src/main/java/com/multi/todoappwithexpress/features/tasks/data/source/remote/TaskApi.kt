package com.multi.todoappwithexpress.features.tasks.data.source.remote

import com.multi.todoappwithexpress.features.tasks.data.source.remote.dto.Task
import com.multi.todoappwithexpress.features.tasks.data.source.remote.dto.ApiResponse
import retrofit2.http.*

interface TaskApi {
    @GET("tasks")
    suspend fun getTasks(): ApiResponse<List<Task>>

    @GET("tasks/{taskId}")
    suspend fun getTaskById(@Path("taskId") taskId: Int): ApiResponse<Task?>

    @POST("tasks")
    suspend fun createTask(@Body task: Task): ApiResponse<Task>

    @PATCH("tasks/{taskId}")
    suspend fun updateTaskById(
        @Path("taskId") taskId: Int,
        @Body task: Task
    ): ApiResponse<Nothing>

    @DELETE("tasks/{taskId}")
    suspend fun deleteTaskById(@Path("taskId") taskId: Int): ApiResponse<Nothing>
}