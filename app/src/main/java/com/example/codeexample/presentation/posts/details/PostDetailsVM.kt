package com.example.codeexample.presentation.posts.details

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

class PostDetailsVM @Inject constructor(
    private val repository: PostsRepository
) : ViewModel() {

    companion object {
        val TAG: String = PostDetailsVM::class.java.simpleName
    }

    private val compositeDisposable = CompositeDisposable()

    private val _postLiveData = MutableLiveData<Resource<Post>>()

    val postLiveData: LiveData<Resource<Post>>
        get() = _postLiveData

    fun getPost(id: Int) {
        _postLiveData.value = Resource.Loading()
        compositeDisposable.add(
            repository.getPost(id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { items -> _postLiveData.postValue(items) },
                    { Log.d(TAG, "Complete") })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}