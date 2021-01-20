package gin.garin.githubuser.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import gin.garin.githubuser.detail.DetailActivity
import gin.garin.githubuser.R
import gin.garin.githubuser.data.User
import gin.garin.githubuser.databinding.ActivityHomeBinding
import gin.garin.githubuser.favorite.FavoriteActivity
import gin.garin.githubuser.settings.SettingsActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.setHasFixedSize(true)

        showRecyclerList()

        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            HomeViewModel::class.java)
        showLoading(true)
        homeViewModel.setUser(getString(R.string.initial_search))

        homeViewModel.getUser().observe(this@HomeActivity, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })

    }

    private fun showRecyclerList() {
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val detail = Intent (this@HomeActivity, DetailActivity::class.java)
                detail.putExtra(DetailActivity.EXTRA_DATA, data.username)
                startActivity(detail)
            }
        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        val delayBuffer :Long = 3000

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                homeViewModel.setUser(query)

                homeViewModel.getUser().observe(this@HomeActivity, { userItems ->
                    if (userItems != null) {
                        adapter.setData(userItems)
                        Handler(mainLooper).postDelayed({
                            showLoading(false)
                        },delayBuffer)
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.show_settings) {
            val mIntent = Intent(this@HomeActivity, SettingsActivity::class.java)
            startActivity(mIntent)
        } else if (item.itemId == R.id.show_favorite){
            val mIntent = Intent(this@HomeActivity, FavoriteActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}