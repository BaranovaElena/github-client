package com.example.githubclient.ui.repodetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubclient.R
import com.example.githubclient.databinding.FragmentRepoDetailBinding
import com.example.githubclient.domain.model.GithubRepoEntity

class RepoDetailFragment : Fragment(R.layout.fragment_repo_detail) {
    private val binding by viewBinding(FragmentRepoDetailBinding::bind)

    companion object {
        private const val BUNDLE_EXTRA_KEY = "REPO_BUNDLE_EXTRA_KEY"
        fun newInstance(repo: GithubRepoEntity) = RepoDetailFragment().apply {
            arguments = Bundle().apply { putParcelable(BUNDLE_EXTRA_KEY, repo) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val githubRepo = arguments?.getParcelable(BUNDLE_EXTRA_KEY) ?: GithubRepoEntity()

        binding.repoDetailNameTextView.text = githubRepo.name
        binding.repoDetailUrlTextView.text = githubRepo.htmlUrl
        binding.repoDetailDescriptionTextView.text = githubRepo.description
        binding.repoDetailCreatedTextView.text = githubRepo.createdAt
        binding.repoDetailUpdatedTextView.text = githubRepo.updatedAt
        binding.repoDetailLanguageTextView.text = githubRepo.language
        binding.repoDetailForksTextView.text = githubRepo.forksCount.toString()
        binding.repoDetailStarsTextView.text = githubRepo.stargazersCount.toString()
    }
}