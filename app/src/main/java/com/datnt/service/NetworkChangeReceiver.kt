package com.datnt.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.widget.Toast

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
                Toast.makeText(context, "wifi on", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "wifi off", Toast.LENGTH_LONG).show()
            }
        }
    }


}


