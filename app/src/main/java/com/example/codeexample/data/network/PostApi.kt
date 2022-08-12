package com.example.codeexample.data.network

import com.example.codeexample.data.models.Post
import com.example.codeexample.data.models.Posts
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApi {

    @GET("posts")
    fun getPosts(): Observable<Posts>

    @GET("posts/{id}")
    fun getPosts(@Path("id") id: Int): Observable<Post>
}