package com.us.prevenircsq.di

import com.us.prevenircsq.data.repository.SettingsRepositoryImpl
import com.us.prevenircsq.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    // Hilt sabrá que cuando alguien pida un SettingsRepository (Dominio),
    // debe proveer un SettingsRepositoryImpl (Datos).
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}