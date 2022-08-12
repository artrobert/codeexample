package com.example.codeexample.di

import com.example.codeexample.ExampleApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ActivityModule::class,
        AndroidInjectionModule::class,
        FragmentsModule::class,
        AppModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(application: ExampleApp)
}