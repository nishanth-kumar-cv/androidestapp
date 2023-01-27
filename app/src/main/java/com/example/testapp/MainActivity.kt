package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.testapp.model.CakeModel
import com.example.testapp.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var fragment: MainFragment
    private var savedInstanceState:Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragment = MainFragment.newInstance()
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        } else {
            fragment.onResponse(savedInstanceState!!.get("cakelist") as List<CakeModel>)
        }
    }

    //Save cakeList to handle orientation changes
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelableArrayList("cakelist",  ArrayList(fragment.getCakeList()))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        this.savedInstanceState = savedInstanceState
    }
}