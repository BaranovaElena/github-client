package com.example.githubclient.ui.userdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubclient.R
import com.example.githubclient.databinding.RepoItemBinding
import com.example.githubclient.domain.model.GithubRepoEntity

class ReposListAdapter(
    private val presenter: UserDetailPresenter
) : RecyclerView.Adapter<ReposListAdapter.ReposListViewHolder>() {
    private var list: List<GithubRepoEntity> = emptyList()

    fun updateList(list: List<GithubRepoEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposListViewHolder =
        ReposListViewHolder(parent, presenter)

    override fun onBindViewHolder(holder: ReposListViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    class ReposListViewHolder(parent: ViewGroup, private val presenter: UserDetailPresenter) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        ) {
        private val binding by viewBinding(RepoItemBinding::bind)
        private val card = itemView as CardView

        fun bind(repo: GithubRepoEntity) {
            binding.repoItemNameTextView.text = repo.name
            card.setOnClickListener {
                presenter.onRepoItemClicked(repo)
            }
        }
    }
}