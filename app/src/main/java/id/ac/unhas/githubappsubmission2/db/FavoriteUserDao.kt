package id.ac.unhas.githubappsubmission2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE id= :id")
    suspend fun checkUser(id : Int) : Int

    @Query("DELETE FROM favorite_user WHERE id = :id")
    suspend fun removeFromFavorite(id : Int) : Int
}