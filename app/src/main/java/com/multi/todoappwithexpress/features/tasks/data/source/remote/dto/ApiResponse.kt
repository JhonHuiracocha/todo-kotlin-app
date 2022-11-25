package com.multi.todoappwithexpress.features.tasks.data.source.remote.dto

data class ApiResponse<T>(
    val data: T? = null,
    val message: String? = null
)