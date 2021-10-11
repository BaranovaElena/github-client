package com.example.githubclient.ui.userslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubclient.R
import com.example.githubclient.databinding.UserItemBinding
import com.example.githubclient.domain.model.UserEntity

class UsersListAdapter : RecyclerView.Adapter<UsersListAdapter.UsersListViewHolder>() {

    private var list: List<UserEntity> = emptyList()

    fun updateList(list: List<UserEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        return UsersListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class UsersListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
    ) {
        private val binding by viewBinding(UserItemBinding::bind)

        fun bind(user: UserEntity) {
            binding.userItemLoginTextView.text = user.name
        }
    }
}