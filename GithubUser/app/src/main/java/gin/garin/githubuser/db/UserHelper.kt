package gin.garin.githubuser.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import gin.garin.githubuser.db.UserContract.UserColumns.Companion.COLUMN_USERNAME
import gin.garin.githubuser.db.UserContract.UserColumns.Companion.TABLE_NAME

class UserHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: UserHelper? = null

        fun getInstance(context: Context): UserHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: UserHelper(context)
                }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                null,
                null)
    }

    fun findByUsername(username: String): Cursor {
        return database.query(DATABASE_TABLE, null, "$COLUMN_USERNAME = ?", arrayOf(username), null, null, null, "1")
    }

    fun checkUsername(username: String): Boolean {
        val cursor = database.query(DATABASE_TABLE, null, "$COLUMN_USERNAME = ?", arrayOf(username), null, null, null, null)
        val sum = cursor.count
        cursor.close()
        return sum > 0
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(username: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$COLUMN_USERNAME = ?", arrayOf(username))
    }

    fun deleteByUsername(username: String): Int {
        return database.delete(DATABASE_TABLE, "$COLUMN_USERNAME = '$username'", null)
    }
}