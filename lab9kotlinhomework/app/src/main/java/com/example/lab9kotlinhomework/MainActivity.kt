package com.example.lab9kotlinhomework

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var flag = false

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val b = intent.extras?: return
            tv_clock?.text =  String.format("%02d:%02d:%02d",
                b.getInt("H"), b.getInt("M"), b.getInt("S"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(receiver, IntentFilter("MyMessage"))

        flag = MyService.flag
        btn_start.text = if(flag) "暂停" else "开始"

        btn_start.setOnClickListener {
            flag = !flag
            btn_start.text = if (flag) "暂停" else "开始"

            Toast.makeText(this, if(flag)"计时开始" else "计时暂停", Toast.LENGTH_SHORT).show()
            startService(Intent(this, MyService::class.java).putExtra("flag", flag))

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(receiver)
    }
}

