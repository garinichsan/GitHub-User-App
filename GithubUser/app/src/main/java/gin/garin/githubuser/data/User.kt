package gin.garin.githubuser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String? = null,
    var name: String? = null,
    var company: String? = null,
    var avatar: String? = null,
    var location: String? = null,
    var following: String? = null,
    var follower: String? = null,
    var repository: String? = null,
): Parcelable
