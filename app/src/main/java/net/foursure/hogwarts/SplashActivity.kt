package net.foursure.hogwarts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.foursure.one.hogwarts.R

class SplashActivity : AppCompatActivity() {
    // Splash screen loading time
    private val SPLASH_TIME_OUT:Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, MainActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}
