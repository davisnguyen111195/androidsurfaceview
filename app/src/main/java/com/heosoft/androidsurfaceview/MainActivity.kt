package com.heosoft.androidsurfaceview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        gameContext = this
        setContentView(GamePanel(this))
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var gameContext : Context
        fun getGameContext(): Context {
            return gameContext
        }
    }
}