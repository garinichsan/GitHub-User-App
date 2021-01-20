package gin.garin.githubuser.db

import android.net.Uri
import android.provider.BaseColumns

object UserContract {

    const val AUTHORITY = "gin.garin.githubuser"
    const val SCHEME = "content"

    class UserColumns: BaseColumns {
        companion object{
            const val TABLE_NAME = "favorite_user"
            const val COLUMN_USERNAME = "username"
            const val COLUMN_NAME = "name"
            const val COLUMN_AVATAR_URL = "avatar_url"
            const val COLUMN_FOLLOWING = "following"
            const val COLUMN_FOLLOWER = "follower"
            const val COLUMN_REPOSITORY = "repository"
            const val COLUMN_COMPANY = "company"
            const val COLUMN_LOCATION = "location"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }
}