package com.hanamin.weather.module

import android.app.Application
import androidx.room.Room
import com.hanamin.weather.data.db.prefs.DataRepository
import com.hanamin.weather.data.db.room.CityListDataBase
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


    @Provides
    @Singleton
    fun databaseProvider(application: Application): CityListDataBase {
        return Room.databaseBuilder(application, CityListDataBase::class.java, "db")
            .build()
    }

}
