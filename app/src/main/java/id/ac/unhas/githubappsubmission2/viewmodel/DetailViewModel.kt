package id.ac.unhas.githubappsubmission2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.ac.unhas.githubappsubmission2.api.RetrofitClient
import id.ac.unhas.githubappsubmission2.data.DetailUserResponse
import id.ac.unhas.githubappsubmission2.db.FavoriteUser
import id.ac.unhas.githubappsubmission2.db.FavoriteUserDao
import id.ac.unhas.githubappsubmission2.db.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val user = MutableLiveData<DetailUserResponse>()
    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failed getting detail", t.message.toString())
                }

                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatar_url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(username, id, avatar_url)
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}