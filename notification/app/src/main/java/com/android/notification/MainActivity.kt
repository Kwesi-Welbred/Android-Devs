package com.android.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.notification.ui.EggTimerFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

          if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, EggTimerFragment.newInstance())
                .commitNow()
        }
    }
}


//https://developer.android.com/codelabs/advanced-android-kotlin-training-notifications?hl=en#4