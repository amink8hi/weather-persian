package com.hanamin.weather.module

import android.app.Application
import com.hanamin.weather.data.db.prefs.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun repositoryProvider(application: Application): DataRepository {
        return DataRepository(application)
    }


}
