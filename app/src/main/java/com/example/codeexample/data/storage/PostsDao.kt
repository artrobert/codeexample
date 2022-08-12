package com.example.codeexample.data.storage

import androidx.room.*
import com.example.codeexample.data.models.Post
import io.reactivex.rxjava3.core.Observable

@Dao
interface PostsDao {
    @Query("SELECT * FROM post")
    fun getAll(): Observable<List<Post>>

    @Query("SELECT * FROM post WHERE id LIKE (:postId)")
    fun getById(postId: Int): Observable<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(post: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post)

    @Delete
    fun delete(post: Post)
}