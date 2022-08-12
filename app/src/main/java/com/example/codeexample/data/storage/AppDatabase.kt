package com.example.codeexample.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codeexample.data.models.Post

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
}