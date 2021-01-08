package gin.garin.githubuser.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import gin.garin.githubuser.R
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

        binding.detailProgressBar.visibility = View.VISIBLE

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
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
                    strFollowing.text = getString(R.string.following)
                    tvDetailFollower.text = userItems.follower
                    val follamt = userItems.follower?.toInt() as Int
                    strFollower.text = resources.getQuantityString(R.plurals.follower,(if(follamt==0) 1 else follamt))
                    tvDetailRepository.text = userItems.repository
                    val repamt = userItems.repository?.toInt() as Int
                    strRepository.text = resources.getQuantityString(R.plurals.repository,(if(repamt==0) 1 else repamt))
                    strCompany.text = getString(R.string.company)
                    tvDetailCompany.text = userItems.company
                    strLocation.text = getString(R.string.location)
                    tvDetailLocation.text = userItems.location
                }
                binding.detailProgressBar.visibility = View.GONE
            }
        })


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f


    }

}