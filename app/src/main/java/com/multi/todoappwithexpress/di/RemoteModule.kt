package com.multi.todoappwithexpress.di

import com.multi.todoappwithexpress.features.tasks.data.repository.TaskRepositoryImpl
import com.multi.todoappwithexpress.features.tasks.data.source.remote.TaskApi
import com.multi.todoappwithexpress.features.tasks.domain.repository.TaskRepository
import com.multi.todoappwithexpress.features.tasks.domain.use_case.*
import com.multi.todoappwithexpress.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit): TaskApi {
        return retrofit.create(TaskApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(taskApi: TaskApi): TaskRepository {
        return TaskRepositoryImpl(taskApi)
    }

    @Singleton
    @Provides
    fun provideNewsUseCase(taskRepository: TaskRepository): TaskUseCase {
        return TaskUseCase(
            createTask = CreateTask(taskRepository),
            getTasks = GetTasks(taskRepository),
            getTaskById = GetTaskById(taskRepository),
            updateTaskById = UpdateTaskById(taskRepository),
            deleteTaskById = DeleteTaskById(taskRepository)
        )
    }
}