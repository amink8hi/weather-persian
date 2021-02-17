package com.hanamin.weather.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientModule {

    val SERVER_TIMEOUT = 30L

    @Provides
    @Singleton
    internal fun okHttpClient(
        application: Application,
        cache: Cache
    ): OkHttpClient {


        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okhttp = OkHttpClient()
            .newBuilder()
            .cache(cache)
            .callTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logging)


        /* // add token in header
         okhttp.addInterceptor { chain ->
             val newRequest = chain.request().newBuilder()
                 .addHeader("Authorization", "Bearer " + DataRepository(application).token)
                 .build()
             chain.proceed(newRequest)

         }*/


        return okhttp.build()
    }

    @Provides
    @Singleton
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, 10.toLong() * 1024 * 1024) // 10 MB
    }

    @Provides
    @Singleton
    fun file(application: Application): File {
        val file = File(application.cacheDir, "HttpCache")
        file.mkdirs()
        return file
    }

}
