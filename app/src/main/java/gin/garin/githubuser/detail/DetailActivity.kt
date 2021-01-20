package gin.garin.githubuser.detail

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import gin.garin.githubuser.R
import gin.garin.githubuser.databinding.ActivityDetailBinding
import gin.garin.githubuser.db.UserContract
import gin.garin.githubuser.db.UserHelper

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    private lateinit var userHelper: UserHelper
    private lateinit var values: ContentValues

    private var favStatus: Boolean = false
    private var username: String = ""
    private lateinit var favIcon: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_DATA) as String

        binding.detailProgressBar.visibility = View.VISIBLE

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
        detailViewModel.setUser(username)

        values = ContentValues()

        detailViewModel.getUser().observe(this@DetailActivity, { userItems ->
            if (userItems != null) {
                with(binding){
                    tvDetailUsername.text = userItems.username
                    tvDetailName.text = userItems.name
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
                values.put(UserContract.UserColumns.COLUMN_USERNAME, userItems.username)
                values.put(UserContract.UserColumns.COLUMN_NAME, userItems.name)
                values.put(UserContract.UserColumns.COLUMN_AVATAR_URL, userItems.avatar)
                values.put(UserContract.UserColumns.COLUMN_FOLLOWING, userItems.following)
                values.put(UserContract.UserColumns.COLUMN_FOLLOWER, userItems.follower)
                values.put(UserContract.UserColumns.COLUMN_REPOSITORY, userItems.repository)
                values.put(UserContract.UserColumns.COLUMN_COMPANY, userItems.company)
                values.put(UserContract.UserColumns.COLUMN_LOCATION, userItems.location)
            }
        })

        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_option_menu, menu)

        favIcon = menu.findItem(R.id.detail_favorite)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.detail_favorite) {
            favStatus = !favStatus
            if (favStatus) userHelper.insert(values) else userHelper.deleteByUsername(username)
            this.invalidateOptionsMenu()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (userHelper.checkUsername(username)) favStatus = true
        if(favStatus){
            favIcon.setIcon(R.drawable.ic_favorite)
        } else {
            favIcon.setIcon(R.drawable.ic_favorite_border)
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        userHelper.close()
    }

}