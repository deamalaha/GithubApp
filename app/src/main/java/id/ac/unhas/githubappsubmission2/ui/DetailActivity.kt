package id.ac.unhas.githubappsubmission2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.ac.unhas.githubappsubmission2.R
import id.ac.unhas.githubappsubmission2.activity.DetailViewModel
import id.ac.unhas.githubappsubmission2.activity.SectionPagerAdapter
import id.ac.unhas.githubappsubmission2.data.DetailUserResponse
import id.ac.unhas.githubappsubmission2.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        if (username != null) {
            detailViewModel.setUserDetail(username)
        }
        detailViewModel.getUserDetail().observe(this, Observer<DetailUserResponse> {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(detailAvatar)
                    detailName.text = it.name
                    detailUsername.text = it.login
                    detailFollowers.text = "${it.followers} Followers"
                    detailFollowing.text = "${it.following} Following"
                }
            }
        })
        
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                binding.toggleFavorite.isChecked = count != null && count > 0
            }
        }

        binding.toggleFavorite.setOnCheckedChangeListener { _, isChecked ->
            username?.let {
                if (isChecked) {
                    avatarUrl?.let { detailViewModel.addToFavorite(username, id, avatarUrl) }
                    Toast.makeText(this, getString(R.string.add_to_favorite), Toast.LENGTH_SHORT).show()
                } else {
                    detailViewModel.removeFavorite(id)
                    Toast.makeText(this, getString(R.string.remove_from_favorite), Toast.LENGTH_SHORT).show()
                }
            }
        }


        //View Pager and TabLayout
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity)
        sectionPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }
}