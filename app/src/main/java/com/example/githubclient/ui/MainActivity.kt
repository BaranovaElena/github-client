package com.example.githubclient.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubclient.R
import com.example.githubclient.ui.userslist.UsersListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUsersListScreen()
    }

    private fun setUsersListScreen() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, UsersListFragment.newInstance())
            .commitNow()
    }
}