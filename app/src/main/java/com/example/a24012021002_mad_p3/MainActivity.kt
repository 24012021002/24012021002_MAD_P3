package com.example.a24012021002_mad_p3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CallLog
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.a24012021002_mad_p3.R

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CALL_PERMISSION_CODE = 101
    }

    private lateinit var etPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUrl = findViewById<EditText>(R.id.et_url)
        etPhone = findViewById(R.id.et_phone)

        val btnBrowse = findViewById<Button>(R.id.btn_browse)
        val btnCall = findViewById<Button>(R.id.btn_call)
        val btnCallLog = findViewById<Button>(R.id.btn_call_log)
        val btnGallery = findViewById<Button>(R.id.btn_gallery)
        val btnCamera = findViewById<Button>(R.id.btn_camera)
        val btnAlarm = findViewById<Button>(R.id.btn_alarm)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        // 1. Open specific URL
        btnBrowse.setOnClickListener {
            val url = etUrl.text.toString().trim()
            val finalUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                "https://$url"
            } else {
                url
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl))
            startActivity(intent)
        }

        // 2. Make call to specific number
        btnCall.setOnClickListener {
            makePhoneCall()
        }

        // 3. Open Call Log
        btnCallLog.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                type = CallLog.Calls.CONTENT_TYPE
            }
            startActivity(intent)
        }

        // 4. Open Gallery
        btnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                type = "image/*"
            }
            startActivity(intent)
        }

        // 5. Open Camera
        btnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }

        // 6. Set Alarm
        btnAlarm.setOnClickListener {
            try {
                val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
                startActivity(intent)
            } catch (e: Exception) {
                val setAlarmIntent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                    putExtra(AlarmClock.EXTRA_MESSAGE, "New Alarm")
                    putExtra(AlarmClock.EXTRA_HOUR, 9)
                    putExtra(AlarmClock.EXTRA_MINUTES, 0)
                }
                startActivity(setAlarmIntent)
            }
        }

        // 7. Open Login Activity (Explicit Intent)
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun makePhoneCall() {
        val phoneNo = etPhone.text.toString().trim()
        if (phoneNo.isEmpty()) {
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            return
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val callIntent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNo")
            }
            startActivity(callIntent)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            } else {
                Toast.makeText(this, "Call permission denied. Opening dialer...", Toast.LENGTH_SHORT).show()
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${etPhone.text.toString().trim()}"))
                startActivity(dialIntent)
            }
        }
    }
}