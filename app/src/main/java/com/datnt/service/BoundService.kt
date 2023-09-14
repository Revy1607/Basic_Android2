package com.datnt.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BoundService : Service() {
    private val binder = MyBinder()

    inner class MyBinder: Binder(){
        fun getBoundServie(): BoundService = this@BoundService
    }

    override fun onCreate() {
        super.onCreate()
    }
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun calculation(a: Int, b: Int): Int{
        return a+b
    }
}