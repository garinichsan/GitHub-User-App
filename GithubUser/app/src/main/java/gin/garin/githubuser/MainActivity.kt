package gin.garin.githubuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import gin.garin.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.setHasFixedSize(true)

        list.addAll(getListUseres())
        showRecyclerList()
    }

    @SuppressLint("Recycle")
    fun getListUseres(): ArrayList<User> {
        val dataUserName = resources.getStringArray(R.array.username)
        val dataName = resources.getStringArray(R.array.name)
        val dataCompany = resources.getStringArray(R.array.company)
        val dataAvatar = resources.obtainTypedArray(R.array.avatar)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataFollowing = resources.getStringArray(R.array.following)
        val dataFollower = resources.getStringArray(R.array.followers)
        val dataRepo = resources.getStringArray(R.array.repository)
        val listUser = ArrayList<User>()
        for (position in dataName.indices) {
            val user = User(
                    dataUserName[position],
                    dataName[position],
                    dataCompany[position],
                    dataAvatar.getResourceId(position, -1),
                    dataLocation[position],
                    dataFollowing[position],
                    dataFollower[position],
                    dataRepo[position],
            )
            listUser.add(user)
        }
        return listUser
    }

    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        binding.rvUser.adapter = listUserAdapter
    }

}