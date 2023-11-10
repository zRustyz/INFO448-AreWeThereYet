package edu.uw.ischool.rliu05.awty

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

class MainActivity : AppCompatActivity() {
    lateinit var textViewMessage: TextView
    lateinit var textViewPhoneNumber: TextView
    lateinit var textViewMinutes: TextView
    lateinit var buttonStart: Button
    private var interval: Long = 60000
    private var status: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewMessage = findViewById(R.id.editTextMessage)
        textViewPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        textViewMinutes = findViewById(R.id.editTextMinutes)
        buttonStart = findViewById(R.id.buttonStart)
        textViewMessage.addTextChangedListener(watcher)
        textViewPhoneNumber.addTextChangedListener(watcher)
        textViewMinutes.addTextChangedListener(watcher)

        buttonStart.isEnabled = false

        buttonStart.setOnClickListener {
            if (buttonStart.text == "Start") {
                interval *= textViewMinutes.text.toString().toLong()
                toggleAlarm()
                buttonStart.text = "Stop"
                Log.i("alarm", "Pushed")
            } else {
                interval = 60000
                toggleAlarm()
                buttonStart.text ="Start"
            }
        }
    }

    private val watcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val messageValid = textViewMessage.text.isNotEmpty()
            val phoneNumberValid = textViewPhoneNumber.text.isNotEmpty()
            val minutesValid = textViewMinutes.text.isNotEmpty()

            buttonStart.isEnabled = messageValid && phoneNumberValid && minutesValid
        }

    }


    private fun toggleAlarm() {
        val activityThis = this
        val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(activityThis, AlarmReciever::class.java).apply {
            putExtra("number", textViewPhoneNumber.text.toString())
            putExtra("message", textViewMessage.text.toString())
        }
        val pendingIntent = PendingIntent.getBroadcast(activityThis, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        if (status) {
            alarmManager.cancel(pendingIntent)
        } else {
            val firstTriggerAtMillis = System.currentTimeMillis()  + interval
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                firstTriggerAtMillis,
                interval,
                pendingIntent)
        }
    }
}




