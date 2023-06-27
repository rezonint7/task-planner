package com.example.task_planner.di

import android.content.Context
import com.example.task_planner.domain.repository.DatabaseService
import com.example.task_planner.data.repository.DatabaseServiceImpl
import com.example.task_planner.data.sharedprefs_helper.SharedPrefsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPrefsHelper(@ApplicationContext context: Context): SharedPrefsHelper {
        return SharedPrefsHelper.getInstance(context)
    }
    @Provides
    @Singleton
    fun providesDatabaseService(): DatabaseService = DatabaseServiceImpl()
}