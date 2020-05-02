package com.kochetov.currencyrates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*

        TechStack:
        MVVM/MVI
        Dagger
        RxJava
        Retrofit
        NavComponent
        TODO: Ktlint for static code analysis
        TODO: MockK for testing
        * */

        /*
        * TODO Inject base without restarting the flwo
        * TODO Custom Errors
        * TODO check if RxJava operators delay, repeat, retry were correct
        * TODO app icon
        * TODO styles
        * TODO cmd l refactoring
        * TODO debug/release
        * TODO add serialized classes to proguard
        * TODO test on production
        *
        * */

    }
}
