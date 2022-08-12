package com.example.codeexample.di

import com.example.codeexample.presentation.posts.PostsFragment
import com.example.codeexample.presentation.posts.details.PostDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributePostsFragment(): PostsFragment

    @ContributesAndroidInjector
    abstract fun contributePostDetailsFragment(): PostDetailsFragment
}