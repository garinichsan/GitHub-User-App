package gin.garin.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val name: String,
    val company: String,
    val avatar: Int,
    val location: String,
    val following: String,
    val follower: String,
    val Repository: String,
): Parcelable
