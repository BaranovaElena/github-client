package com.example.githubclient.ui

import android.content.Context
import com.example.githubclient.App

val Context.app: App
    get() {
        return applicationContext as App
    }