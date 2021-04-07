package id.ac.unhas.githubappsubmission2.api

import id.ac.unhas.githubappsubmission2.data.DetailUserResponse
import id.ac.unhas.githubappsubmission2.data.User
import id.ac.unhas.githubappsubmission2.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("/search/users?")
    @Headers("Authorization: token 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")
    fun getSearchUsers( @Query("q") query: String): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(@Path("username") username : String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username : String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username : String): Call<ArrayList<User>>
}