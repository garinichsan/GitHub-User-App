package gin.garin.githubuser.favorite

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import gin.garin.githubuser.data.User
import gin.garin.githubuser.databinding.ActivityFavoriteBinding
import gin.garin.githubuser.db.UserContract
import gin.garin.githubuser.db.UserHelper
import gin.garin.githubuser.detail.DetailActivity
import gin.garin.githubuser.home.ListUserAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var userHelper: UserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUserFavorite.setHasFixedSize(true)

        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()

        showRecyclerList()

        loadUserAsync()

    }


    private fun showRecyclerList() {
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvUserFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvUserFavorite.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val detail = Intent (this@FavoriteActivity, DetailActivity::class.java)
                detail.putExtra(DetailActivity.EXTRA_DATA, data.username)
                startActivity(detail)
            }
        })

    }

    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            showLoading(true)
            val deferredUser = async(Dispatchers.IO) {
                val cursor = userHelper.queryAll()
                cursorToList(cursor)
            }
            showLoading(false)
            adapter.setData(deferredUser.await())
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarFavorite.visibility = View.VISIBLE
        } else {
            binding.progressBarFavorite.visibility = View.GONE
        }
    }

    private fun cursorToList(userCursor: Cursor): ArrayList<User>{
        val userList = ArrayList<User>()
        userCursor.apply {
            while (moveToNext()) {
                val user = User()
                user.username = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_USERNAME))
                user.name = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME))
                user.avatar = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_AVATAR_URL))
                userList.add(user)
            }
        }
        return userList
    }

    override fun onDestroy() {
        super.onDestroy()
        userHelper.close()
    }
}