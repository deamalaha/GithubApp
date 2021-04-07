package id.ac.unhas.githubappsubmission2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.unhas.githubappsubmission2.activity.FollowingViewModel
import id.ac.unhas.githubappsubmission2.activity.UserAdapter
import id.ac.unhas.githubappsubmission2.data.User
import id.ac.unhas.githubappsubmission2.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FollowingViewModel

    companion object {
        @JvmStatic
        private val ARG_USERNAME = "username"
        fun newInstance(username: String?) : Fragment {
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME).toString()

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = adapter
        }

        showLoading(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner, Observer<ArrayList<User>> {
            if (it!= null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state : Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}