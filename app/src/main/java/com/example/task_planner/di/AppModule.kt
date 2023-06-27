package com.example.task_planner.di

import com.example.task_planner.domain.repository.DatabaseService
import com.example.task_planner.data.repository.DatabaseServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDatabaseService(): DatabaseService = DatabaseServiceImpl()
}