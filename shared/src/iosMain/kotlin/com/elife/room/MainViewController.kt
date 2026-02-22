package com.elife.room

import androidx.compose.ui.window.ComposeUIViewController
import com.elife.room.di.initKoin
import com.elife.room.ui.App

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }