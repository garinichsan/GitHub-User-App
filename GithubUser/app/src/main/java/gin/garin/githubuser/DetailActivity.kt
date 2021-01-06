package gin.garin.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_DATA) as String

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detailViewModel.setUser(username)

        detailViewModel.getUser().observe(this@DetailActivity, { userItems ->
            if (userItems != null) {
                with(binding){
                    tvDetailName.text = userItems.name
                    tvDetailUsername.text = userItems.username
                    Glide.with(this@DetailActivity.applicationContext)
                        .load(userItems.avatar)
                        .apply(RequestOptions().override(55, 55))
                        .into(profilPict)
                    tvDetailFollowing.text = userItems.following
                    tvDetailFollower.text = userItems.follower
                    tvDetailRepository.text = userItems.repository
                    tvDetailCompany.text = userItems.company
                    tvDetailLocation.text = userItems.location
                }
            }
        })


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f


    }
}