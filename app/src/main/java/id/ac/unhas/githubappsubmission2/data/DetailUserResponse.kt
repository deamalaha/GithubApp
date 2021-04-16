package id.ac.unhas.githubappsubmission2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class DetailUserResponse(
    val login: String,
    val id: Int,
    val name: String,
    val avatar_url: String,
    val followers: Int,
    val following: Int,
)