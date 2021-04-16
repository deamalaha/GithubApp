package id.ac.unhas.githubappsubmission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.unhas.githubappsubmission2.api.RetrofitClient
import id.ac.unhas.githubappsubmission2.data.User
import id.ac.unhas.githubappsubmission2.data.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<User>>()

    fun setUser(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    }
                }
            })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUser
    }
}