package com.example.githubclient.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubclient.R
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val navigator by lazy { AppNavigator(this, R.id.main_container) }
    private val router: Router by inject()
    private val navigatorHolder: NavigatorHolder by inject()

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            router.newRootScreen(Screens.usersList())
        }
    }
}