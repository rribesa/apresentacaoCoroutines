package com.rribesa.coroutinesapresentacao

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadinExampleFragment()
    }

    private fun loadinExampleFragment() {
        this.supportFragmentManager.let {
            val ft = it.beginTransaction()
            ft.replace(R.id.main_container, ExemploLifeCycleScopeFragment())
            ft.commit()
        }
    }
}