package com.example.githubclient.ui.userdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.githubclient.R
import com.example.githubclient.databinding.FragmentUserDetailBinding
import com.example.githubclient.domain.model.GithubRepoEntity
import com.example.githubclient.domain.model.UserEntity
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserDetailFragment : MvpAppCompatFragment(R.layout.fragment_user_detail),
    UserDetailContract.View {
    private val binding by viewBinding(FragmentUserDetailBinding::bind)
    private val presenter by moxyPresenter { UserDetailPresenter() }
    private val adapter: ReposListAdapter by lazy { ReposListAdapter(presenter) }

    companion object {
        private const val BUNDLE_EXTRA_KEY = "USER_BUNDLE_EXTRA_KEY"

        fun newInstance(user: UserEntity) = UserDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_EXTRA_KEY, user)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = arguments?.getParcelable(BUNDLE_EXTRA_KEY) ?: UserEntity()
        presenter.onViewCreated(currentUser.githubUser)

        binding.userDetailNameTextView.text = currentUser.githubUser.login
        binding.userDetailHtmlTextView.text = currentUser.githubUser.htmlUrl
        binding.userDetailRatingTextView.text = currentUser.rating.toString()
        Glide.with(binding.userDetailAvatarImageView.context)
            .load(currentUser.githubUser.avatarUrl)
            .circleCrop()
            .into(binding.userDetailAvatarImageView)

        binding.userDetailLikeTextView.setOnClickListener { presenter.onLikeClicked(currentUser) }
        binding.userDetailDislikeTextView.setOnClickListener { presenter.onDislikeClicked(currentUser) }

        binding.reposListRecyclerView.adapter = adapter
        binding.reposListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun showLikeCount(count: Int) {
        binding.userDetailLikeTextView.text = "$count"
    }

    override fun showDislikeCount(count: Int) {
        binding.userDetailDislikeTextView.text = "$count"
    }

    override fun showRating(rating: Int) {
        binding.userDetailRatingTextView.text = rating.toString()
    }

    override fun showReposList(list: List<GithubRepoEntity>) {
        adapter.updateList(list)
    }

    override fun showLoadRepoError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}