package id.ac.unhas.githubappsubmission2.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.unhas.githubappsubmission2.db.FavoriteUser
import id.ac.unhas.githubappsubmission2.db.FavoriteUserDao
import id.ac.unhas.githubappsubmission2.db.UserDatabase

class FavoriteViewModel (application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }

}