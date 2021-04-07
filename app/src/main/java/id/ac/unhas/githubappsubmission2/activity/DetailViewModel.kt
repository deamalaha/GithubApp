package id.ac.unhas.githubappsubmission2.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.unhas.githubappsubmission2.api.RetrofitClient
import id.ac.unhas.githubappsubmission2.data.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    val user = MutableLiveData<DetailUserResponse>()

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }
}