package com.example.myapp

import android.app.NotificationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.clevertap.android.sdk.CleverTapAPI
import java.util.HashMap


class MainActivity : ComponentActivity() {
    private var cleverTapAPI: CleverTapAPI? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cleverTapAPI = CleverTapAPI.getDefaultInstance(applicationContext);
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        cleverTapAPI?.pushEvent("Product Viewed")


        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnTestEvent = findViewById<Button>(R.id.btnTestEvent)


        // Handle Login Button
        btnLogin.setOnClickListener { view: View? ->
            val name = etName.text.toString().trim { it <= ' ' }
            val email = etEmail.text.toString().trim { it <= ' ' }
            if (!name.isEmpty() && !email.isEmpty()) {
                val profileUpdate = HashMap<String, Any>()
                profileUpdate["Name"] = name
                profileUpdate["Email"] = email

                if (cleverTapAPI != null) {
                    cleverTapAPI!!.onUserLogin(profileUpdate)
                    Toast.makeText(this@MainActivity, "User Logged In", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter valid details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        btnTestEvent.setOnClickListener { view: View? ->
            if (cleverTapAPI != null) {
                cleverTapAPI!!.pushEvent("TEST")
                Toast.makeText(
                    this@MainActivity,
                    "TEST Event Raised",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        cleverTapAPI?.createNotificationChannel(
            applicationContext,
            "test_channel",
            "Test Channel",
            "Channel for Test Notifications",
            NotificationManager.IMPORTANCE_HIGH,
            true
        );

    }
}


