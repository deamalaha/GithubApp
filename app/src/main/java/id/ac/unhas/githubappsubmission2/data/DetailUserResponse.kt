package id.ac.unhas.githubappsubmission2.data

data class DetailUserResponse(
    val login: String,
    val name: String,
    val avatar_url: String,
    val followers : Int,
    val following : Int
)