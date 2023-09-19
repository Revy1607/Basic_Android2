package com.datnt.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    private var btnPlayMusic: Button? = null
    private var btnStopMusic: Button? = null
    private var btnboundService: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlayMusic = findViewById(R.id.btnPlayMusic)
        btnStopMusic = findViewById(R.id.btnStopMusic)
        btnboundService = findViewById(R.id.boundService)

        btnPlayMusic?.setOnClickListener {
            val intent = Intent(this, ForegroundServiceSong::class.java)
            startService(intent)
        }


        btnStopMusic?.setOnClickListener {
            stopService(Intent(this, ForegroundServiceSong::class.java))
        }

        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        val networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, intentFilter)

        btnboundService?.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
        }
    }

}