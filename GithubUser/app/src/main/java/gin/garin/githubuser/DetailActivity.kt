package gin.garin.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import gin.garin.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<User>(EXTRA_DATA) as User

        with(binding){
            tvDetailName.text = data.name
            tvDetailUsername.text = data.username
            Glide.with(this@DetailActivity.applicationContext)
                    .load(data.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(profilPict)
            tvDetailFollowing.text = data.following
            tvDetailFollower.text = data.follower
            tvDetailRepository.text = data.repository
            tvDetailCompany.text = data.company
            tvDetailLocation.text = data.location
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f


    }
}