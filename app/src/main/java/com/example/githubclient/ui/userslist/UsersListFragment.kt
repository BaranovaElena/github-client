package com.example.githubclient.ui.userslist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubclient.R
import com.example.githubclient.databinding.FragmentUsersListBinding
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.users.DataSource
import com.example.githubclient.ui.app
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersListFragment : MvpAppCompatFragment(R.layout.fragment_users_list),
    UsersListContract.View {
    private val binding by viewBinding(FragmentUsersListBinding::bind)
    private val presenter by moxyPresenter {
        UsersListPresenter(
            requireActivity().app.router,
            requireActivity().app.usersRepo,
            requireActivity().app.ratingRepo
        )
    }
    private val adapter: UsersListAdapter by lazy { UsersListAdapter(presenter) }

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

    override fun showError(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun showWarning(source: DataSource) {
        when (source) {
            DataSource.WEB_SOURCE -> {}
            else -> {
                Toast.makeText(
                    context,
                    when (source) {
                        DataSource.DATABASE_SOURCE -> R.string.source_warning_database
                        DataSource.DUMMY_SOURCE -> R.string.source_warning_dummy
                        else -> R.string.source_warning_unknown
                    },
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}