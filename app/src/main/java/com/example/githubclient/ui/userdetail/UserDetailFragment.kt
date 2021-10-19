package com.example.githubclient.ui.userdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubclient.R
import com.example.githubclient.databinding.FragmentUserDetailBinding
import com.example.githubclient.domain.model.UserEntity

class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {
    private val binding by viewBinding(FragmentUserDetailBinding::bind)

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

        binding.userDetailNameValueTextView.text =
            arguments?.getParcelable<UserEntity>(BUNDLE_EXTRA_KEY)?.name ?: UserEntity().name
        binding.userDetailRatingValueTextView.text =
            arguments?.getParcelable<UserEntity>(BUNDLE_EXTRA_KEY)?.rating.toString()

        binding.userDetailLikeTextView.setOnClickListener {  }
        binding.userDetailDislikeTextView.setOnClickListener {  }
    }
}