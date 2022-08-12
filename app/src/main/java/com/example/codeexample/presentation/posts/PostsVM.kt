package com.example.codeexample.presentation.posts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codeexample.data.Resource
import com.example.codeexample.data.models.Post
import com.example.codeexample.data.repository.PostsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class PostsVM @Inject constructor(
    private val repository: PostsRepository
) : ViewModel() {

    companion object {
        private val TAG = PostsVM::class.java.simpleName
    }

    private val compositeDisposable = CompositeDisposable()

    private val _postsLiveData = MutableLiveData<Resource<List<Post>>>()

    val postsLiveData: LiveData<Resource<List<Post>>>
        get() = _postsLiveData

    init {
        getPosts()
    }

    private fun getPosts() {
        _postsLiveData.value = Resource.Loading()
        compositeDisposable.add(
            repository.getPosts().observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { items -> _postsLiveData.postValue(items) },
                    { Log.d(TAG, "Complete") })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}