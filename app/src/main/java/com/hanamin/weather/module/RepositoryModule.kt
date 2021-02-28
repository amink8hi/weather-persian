package com.hanamin.weather.module

import android.app.Application
import androidx.room.Room
import com.hanamin.weather.data.db.prefs.DataRepository
import com.hanamin.weather.data.db.room.RoomDataBase
import com.hanamin.weather.utils.FileUtils
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
    fun fileProvider(application: Application): FileUtils {
        return FileUtils(application)
    }


    @Provides
    @Singleton
    fun databaseProvider(application: Application): RoomDataBase {
        return Room.databaseBuilder(application, RoomDataBase::class.java, "db")
            .build()
    }

}
