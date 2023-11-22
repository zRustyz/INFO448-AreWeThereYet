package edu.uw.ischool.rliu05.awty

import android.app.IntentService
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast

class AwtyService : IntentService("AwtyService"){
    override fun onHandleIntent(intent: Intent?) {
        val number = intent?.getStringExtra("number")
        val message = intent?.getStringExtra("message")
        val smsManager = SmsManager.getDefault()
        Log.i("AwtyService", "Text Sent, $number:$message")
        smsManager.sendTextMessage(number, null, message, null, null)
    }

}