package com.example.miniprojeto.di

import android.content.Context
import com.example.miniprojeto.data.database.SensorDatabaseHelper
import com.example.miniprojeto.data.repository.SensorRepository
import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import com.example.miniprojeto.domain.usecase.*
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
    fun provideSensorDatabaseHelper(
        @ApplicationContext context: Context
    ): SensorDatabaseHelper = SensorDatabaseHelper(context)

    @Provides
    @Singleton
    fun provideSensorRepository(
        dbHelper: SensorDatabaseHelper
    ): SensorRepositoryInterface = SensorRepository(dbHelper)

    @Provides
    fun provideSaveAccelerometerReadingUseCase(
        repository: SensorRepositoryInterface
    ) = SaveAccelerometerReadingUseCase(repository)

    @Provides
    fun provideSaveLightReadingUseCase(
        repository: SensorRepositoryInterface
    ) = SaveLightReadingUseCase(repository)

    @Provides
    fun provideGetAllReadingsUseCase(
        repository: SensorRepositoryInterface
    ) = GetAllReadingsUseCase(repository)

    @Provides
    fun provideClearAllReadingsUseCase(
        repository: SensorRepositoryInterface
    ) = ClearAllReadingsUseCase(repository)

    @Provides
    fun provideDeleteReadingUseCase(
        repository: SensorRepositoryInterface
    ) = DeleteReadingUseCase(repository)
}

