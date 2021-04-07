package id.ac.unhas.githubappsubmission2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)


        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java
        )
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


        //View Pager and TabLayout
        val sectionPagerAdapter =
            SectionPagerAdapter(this)
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