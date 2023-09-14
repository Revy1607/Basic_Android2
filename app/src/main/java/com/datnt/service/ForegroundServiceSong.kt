package com.datnt.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.session.PlaybackState.ACTION_STOP
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class ForegroundServiceSong : Service() {
    companion object{
        const val ACTION_STOP = "com.yourpackage.ACTION_STOP"
    }
    private val binder = LocalBinder()
    private var mediaPlayer: MediaPlayer? = null
    private val channelId = "music_channel"

    inner class LocalBinder : Binder() {
        fun getService(): ForegroundServiceSong = this@ForegroundServiceSong
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.water)
        mediaPlayer?.isLooping = true

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                "MusicChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE)

        val stopIntent = Intent(this, ForegroundServiceSong::class.java)
        stopIntent.action = ACTION_STOP
        val pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Tạo notification
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Music Player")
            .setContentText("Now playing your music")
            .setSmallIcon(R.drawable.baseline_music_note_24)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.images))
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.baseline_stop_24, "stop", pendingStopIntent)
            .build()

        if (intent?.action == ACTION_STOP) {
            stopMusic()
            return START_NOT_STICKY
        }

        // Bắt đầu dịch vụ trong chế độ foreground
        startForeground(1, notification)

        mediaPlayer?.start() // Bắt đầu phát nhạc

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun stopMusic() {
        mediaPlayer?.stop()
        stopForeground(true) // Dừng chế độ foreground
        stopSelf() // Dừng dịch vụ
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}