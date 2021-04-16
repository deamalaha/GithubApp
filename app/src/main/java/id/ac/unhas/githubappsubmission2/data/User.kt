package id.ac.unhas.githubappsubmission2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id : Int,
    val login : String,
    val avatar_url : String
): Parcelable