package gin.garin.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            profilPict.setImageResource(data.avatar)
            tvDetailFollowing.text = data.following
            tvDetailFollower.text = data.follower
            tvDetailRepository.text = data.repository
            tvDetailCompany.text = data.company
            tvDetailLocation.text = data.location
        }


    }
}