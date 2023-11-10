package edu.uw.ischool.rliu05.awty

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReciever: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val number = intent?.getStringExtra("number")
        val message = intent?.getStringExtra("message")
        Toast.makeText(context, "$number:$message", Toast.LENGTH_SHORT).show()
    }
}