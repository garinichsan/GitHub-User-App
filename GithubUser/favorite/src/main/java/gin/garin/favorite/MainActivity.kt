package gin.garin.favorite

import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import gin.garin.favorite.UserContract.UserColumns.Companion.CONTENT_URI
import gin.garin.favorite.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.setHasFixedSize(true)

        showRecyclerList()

        loadUserAsync()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadUserAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

    }


    private fun showRecyclerList() {
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter

    }

    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            showLoading(true)
            val deferredUser = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                cursorToList(cursor)
            }
            showLoading(false)
            adapter.setData(deferredUser.await())
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun cursorToList(userCursor: Cursor?): ArrayList<User>{
        val userList = ArrayList<User>()
        userCursor?.apply {
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
    }
}