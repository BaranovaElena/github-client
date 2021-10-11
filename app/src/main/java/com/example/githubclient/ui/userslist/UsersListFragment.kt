package com.example.githubclient.ui.userslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubclient.R
import com.example.githubclient.databinding.FragmentUsersListBinding

class UsersListFragment : Fragment(R.layout.fragment_users_list), UsersListContract.View {
    private val binding by viewBinding(FragmentUsersListBinding::bind)
    private val presenter: UsersListContract.Presenter = UsersListPresenter()

    companion object {
        fun newInstance() = UsersListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        presenter.onViewCreated()
    }

    override fun showLoginsAsString(logins: String) {
        binding.usersListTextView.text = logins
    }
}