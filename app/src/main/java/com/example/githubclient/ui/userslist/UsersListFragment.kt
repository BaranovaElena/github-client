package com.example.githubclient.ui.userslist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubclient.R
import com.example.githubclient.databinding.FragmentUsersListBinding
import com.example.githubclient.domain.model.UserEntity
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersListFragment : MvpAppCompatFragment(R.layout.fragment_users_list), UsersListContract.View {
    private val binding by viewBinding(FragmentUsersListBinding::bind)
    private val presenter by moxyPresenter { UsersListPresenter() }
    private val adapter: UsersListAdapter by lazy { UsersListAdapter() }

    companion object {
        fun newInstance() = UsersListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usersListRecyclerView.adapter = adapter
        binding.usersListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun showUsersList(list: List<UserEntity>) {
        adapter.updateList(list)
    }
}