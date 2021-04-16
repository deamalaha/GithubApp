package id.ac.unhas.githubappsubmission2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.unhas.githubappsubmission2.activity.FavoriteViewModel
import id.ac.unhas.githubappsubmission2.activity.UserAdapter
import id.ac.unhas.githubappsubmission2.data.User
import id.ac.unhas.githubappsubmission2.databinding.ActivityFavoriteBinding
import id.ac.unhas.githubappsubmission2.db.FavoriteUser

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: UserAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteAdapter = UserAdapter()
        favoriteAdapter.notifyDataSetChanged()
        favoriteAdapter.setOnItemClickCallback(object :
            UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = favoriteAdapter
        }

        favoriteViewModel.getFavoriteUser()?.observe(this, Observer<List<FavoriteUser>> {
            if (it != null) {
                val list = mapList(it)
                favoriteAdapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.id,
                user.login,
                user.avatar_url
            )
            listUser.add(userMapped)
        }
        return listUser
    }
}