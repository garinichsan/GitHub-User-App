package gin.garin.githubuser.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import gin.garin.githubuser.db.UserContract.UserColumns.Companion.TABLE_NAME

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {

        private const val DATABASE_NAME = "dbfavorite"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                " (${UserContract.UserColumns.COLUMN_USERNAME} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_NAME} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_AVATAR_URL} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_FOLLOWING} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_FOLLOWER} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_REPOSITORY} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_LOCATION} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_COMPANY} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}