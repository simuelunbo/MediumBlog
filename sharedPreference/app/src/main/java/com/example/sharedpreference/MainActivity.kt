package com.example.sharedpreference

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import java.security.Security

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            this,
            "EncryptedSharedPreferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        sharedPreferences.edit {
            putString("isSecure", "yes")
        }


        val prefs = getPreferences(MODE_PRIVATE)
        prefs.edit {
            putBoolean("firstBoolean", true)
            putInt("firstInt", 1)
        }
        val firstBoolean = prefs.getBoolean("firstBoolean", false)
        val firstInt = prefs.getInt("firstInt", 0)

        val shared = getSharedPreferences("shared", MODE_PRIVATE)
        shared.edit {
            putBoolean("secondBoolean", false)
            putInt("secondInt", 2)
        }
        val secondBoolean = shared.getBoolean("secondBoolean", true)
        val secondInt = shared.getInt("secondInt", 0)


        Log.e("####First####", "firstBoolean : $firstBoolean  firstInt: $firstInt")
        Log.e("####Second####", "secondBoolean : $secondBoolean  secondInt: $secondInt")
        Log.e("####Secure####", "isSecure: $", )
    }
}