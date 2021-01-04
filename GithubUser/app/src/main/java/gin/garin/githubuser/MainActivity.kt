package gin.garin.githubuser

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gin.garin.githubuser.DetailActivity.Companion.EXTRA_DATA
import gin.garin.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var logoAnim : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Handler().postDelayed(Runnable {
            val home = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(home)
            finish()
        }, 3000)

        logoAnim = AnimationUtils.loadAnimation(this,R.anim.logo_animation)

        val image : ImageView = findViewById(R.id.logo)

        image.animation = logoAnim
    }
}