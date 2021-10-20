package com.example.githubclient.ui.userdetail

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubclient.R
import com.example.githubclient.databinding.FragmentUserDetailBinding
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.ui.app
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserDetailFragment : MvpAppCompatFragment(R.layout.fragment_user_detail),
    UserDetailContract.View {
    private val binding by viewBinding(FragmentUserDetailBinding::bind)
    private val presenter by moxyPresenter { UserDetailPresenter(requireActivity().app.ratingBus) }

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

        arguments?.getParcelable<UserEntity>(BUNDLE_EXTRA_KEY)?.let { user ->
            binding.userDetailNameValueTextView.text = user.name
            binding.userDetailRatingValueTextView.text = user.rating.toString()

            binding.userDetailLikeTextView.setOnClickListener { presenter.onLikeClicked(user) }
            binding.userDetailDislikeTextView.setOnClickListener { presenter.onDislikeClicked(user) }
        } ?: kotlin.run {
            binding.userDetailNameValueTextView.text = UserEntity().name
            binding.userDetailRatingValueTextView.text = UserEntity().rating.toString()
        }
    }

    override fun showLikeCount(count: Int) {
        binding.userDetailLikeTextView.text = "$count"
    }

    override fun showDislikeCount(count: Int) {
        binding.userDetailDislikeTextView.text = "$count"
    }
}