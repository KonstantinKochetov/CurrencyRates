package com.kochetov.currencyrates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*

        Architecture: MVVM/MVI

        TechStack:
        Dagger
        TODO RxJava
        TODO Retrofit
        NavComponent
        TODO: Ktlint for static code analysis
        TODO: MockK for testing

        * */

        /*
        * TODO Rotation kills it
        * TODO check if RxJava operators delay and repeat were correct
        * TODO use observable instead of Single?
        * TODO adding to compositeDisposables two times
        * TODO flag icons
        * TODO app icon
        * TODO styles
        * TODO  cmd l refactoring
        * TODO debug/release
        * TODO add serialized classes to proguard
        * TODO test on production
        * TODO check provides schedulers
        *
        * */

    }
}
