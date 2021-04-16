package id.ac.unhas.githubappsubmission2.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.ac.unhas.githubappsubmission2.ui.FollowersFragment
import id.ac.unhas.githubappsubmission2.ui.FollowingFragment

class SectionPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String? = null

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }
        return fragment as Fragment
    }
}