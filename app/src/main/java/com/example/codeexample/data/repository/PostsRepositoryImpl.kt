package com.example.codeexample.data.repository

import android.util.Log
import com.example.codeexample.data.Resource
import com.example.codeexample.data.Source
import com.example.codeexample.data.models.Post
import com.example.codeexample.data.network.PostsApi
import com.example.codeexample.data.storage.AppDatabase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val networkApi: PostsApi,
    private val database: AppDatabase
) : PostsRepository {

    companion object {
        private val TAG = PostsRepository::class.java.simpleName
    }

    override fun getPosts(): Observable<Resource<List<Post>>> =
        Observable.merge(
            database.postsDao().getAll()
                .subscribeOn(Schedulers.computation())
                .map { Resource.Success(it, Source.LOCAL) },
            networkApi.getPosts()
                .subscribeOn(Schedulers.computation())
                .doOnNext {
                    Log.d(TAG, "GetAll call was successful")
                    database.postsDao().insertAll(it)
                }
                .map { Resource.Success(it, Source.REMOTE) })

    override fun getPost(id: Int): Observable<Resource<Post>> =
        Observable.concat(
            database.postsDao().getById(id)
                .subscribeOn(Schedulers.io())
                .map { Resource.Success(it, Source.LOCAL) },
            networkApi.getPosts(id)
                .subscribeOn(Schedulers.io())
                .doOnNext {
                    Log.d(TAG, "Get call for $id was successful")
                    database.postsDao().insert(it)
                }
                .map { Resource.Success(it, Source.REMOTE) })

}