package id.ac.unhas.githubappsubmission2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.unhas.githubappsubmission2.activity.MainViewModel
import id.ac.unhas.githubappsubmission2.activity.UserAdapter
import id.ac.unhas.githubappsubmission2.data.User
import id.ac.unhas.githubappsubmission2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        userAdapter.setOnItemClickCallback(object :
            UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }

        })

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        
        binding.apply { 
            rvItem.layoutManager = LinearLayoutManager(this@MainActivity)
            rvItem.setHasFixedSize(true)
            rvItem.adapter = userAdapter
            
            btnSearch.setOnClickListener { searchUser() }
            
            searchQuery.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        mainViewModel.getUser().observe(this, Observer<ArrayList<User>> {
            if (it!=null) {
                userAdapter.setList(it)
                showLoading(false)
            }
        })
        
    }

    private fun showLoading(state : Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun searchUser() {
        binding.apply {
            val query = searchQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            mainViewModel.setUser(query)
        }
    }

}