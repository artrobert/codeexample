package com.example.codeexample.di

import android.content.Context
import androidx.room.Room
import com.example.codeexample.data.network.PostsApi
import com.example.codeexample.data.network.RemoteDataSource
import com.example.codeexample.data.repository.PostsRepository
import com.example.codeexample.data.repository.PostsRepositoryImpl
import com.example.codeexample.data.storage.AppDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "database-name").build()

    @Singleton
    @Provides
    fun providesPostsApi(remoteDataSource: RemoteDataSource): PostsApi {
        return remoteDataSource.buildApi(PostsApi::class.java)
    }

    @Singleton
    @Provides
    fun providesPostsRepository(locationApi: PostsApi, database: AppDatabase): PostsRepository {
        return PostsRepositoryImpl(locationApi, database)
    }

    @Singleton
    @Provides
    fun providesGson() = Gson()
}