package gin.garin.githubuser.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import gin.garin.githubuser.BuildConfig
import gin.garin.githubuser.data.User
import org.json.JSONArray
import org.json.JSONObject

class DetailViewModel: ViewModel() {

    val dataUser = MutableLiveData<User>()
    val listUser = MutableLiveData<ArrayList<User>>()

    fun setUser(keyword: String) {
        val url = "https://api.github.com/users/${keyword}"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        client.addHeader("User-agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val user = JSONObject(result)
                    val userItems = User()

                    userItems.username = user.getString("login")
                    userItems.name = if (user.getString("name") != "null") user.getString("name") else "-"
                    userItems.avatar = user.getString("avatar_url")
                    userItems.following = user.getString("following")
                    userItems.follower = user.getString("followers")
                    userItems.repository = user.getString("public_repos")
                    userItems.company = if (user.getString("company") != "null") user.getString("company") else "-"
                    userItems.location = if (user.getString("location") != "null") user.getString("location") else "-"

                    dataUser.postValue(userItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    fun getUser(): LiveData<User> {
        return dataUser
    }

    fun setFollow(url: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        client.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        client.addHeader("User-agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        val user = items.getJSONObject(i)
                        val userItems = User()
                        userItems.username = user.getString("login")
                        userItems.avatar = user.getString("avatar_url")

                        listItems.add(userItems)
                    }
                    listUser.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    fun getFollow(): LiveData<ArrayList<User>> {
        return listUser
    }
}