package gin.garin.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import gin.garin.githubuser.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var logoAnim : Animation
    private val splashDuration:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(mainLooper).postDelayed(Runnable {
            val home = Intent(this@SplashScreenActivity, HomeActivity::class.java)
            startActivity(home)
            finish()
        }, splashDuration)

        logoAnim = AnimationUtils.loadAnimation(this,R.anim.logo_animation)

        val image : ImageView = findViewById(R.id.logo)

        image.animation = logoAnim
    }
}