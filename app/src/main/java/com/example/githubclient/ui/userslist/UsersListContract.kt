package com.example.githubclient.ui.userslist

import com.example.githubclient.domain.model.UserEntity

class UsersListContract {
    interface View {
        fun showUsersList(list: List<UserEntity>)
    }

    interface Presenter {
        fun onAttach(view: View)
        fun onDetach()
        fun onViewCreated()
    }
}