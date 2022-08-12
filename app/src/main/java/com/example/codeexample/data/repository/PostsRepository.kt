package com.example.codeexample.data.repository

import com.example.codeexample.data.Resource
import com.example.codeexample.data.models.Post
import io.reactivex.rxjava3.core.Observable

interface PostsRepository {

    fun getPosts(): Observable<Resource<List<Post>>>

    fun getPost(id: Int): Observable<Resource<Post>>
}