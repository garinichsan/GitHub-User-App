package gin.garin.githubuser.favorite

import android.database.Cursor
import gin.garin.githubuser.data.User
import gin.garin.githubuser.db.UserContract

class MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<User> {
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
}
