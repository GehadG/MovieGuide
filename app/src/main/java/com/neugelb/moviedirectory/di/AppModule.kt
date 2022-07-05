package com.neugelb.moviedirectory.di

import android.content.Context
import androidx.room.Room
import com.neugelb.moviedirectory.network.ApiService
import com.neugelb.moviedirectory.network.AuthInterceptor
import com.neugelb.moviedirectory.persistence.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiService(
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )

            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesDB(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

}