package com.datnt.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Activity2 : AppCompatActivity() {
    private var btnStart: Button? = null
    private var btnStop: Button? = null
    private var edtA: EditText? = null
    private var edtB: EditText? = null

    private lateinit var boundService: BoundService
    private var isBound = false

    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.MyBinder
            boundService = binder.getBoundServie()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        edtA = findViewById(R.id.edtA)
        edtB = findViewById(R.id.edtB)

        val intent = Intent(this, BoundService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        btnStart?.setOnClickListener {
            startBoundService()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isBound){
            unbindService(serviceConnection)
        }
    }

    private fun startBoundService(){
        if(isBound){
            val numberA = edtA?.text.toString().toInt()
            val numberB = edtB?.text.toString().toInt()
            val result = boundService.calculation(numberA, numberB)
            Toast.makeText(this, "Result: $result", Toast.LENGTH_LONG).show()
        }
    }
}