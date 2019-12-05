package com.example.rental.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.rental.R
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {

    private val handler = Handler()

    private val runnable = { startNextActivity() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        startNextFlow()
    }

    private fun startNextFlow() {
        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(2))
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    private fun startNextActivity() {
        val newIntent = Intent(this, MainActivity::class.java)
        startActivity(newIntent)
        finish()
    }

}
