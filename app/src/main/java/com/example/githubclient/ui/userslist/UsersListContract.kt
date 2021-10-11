package com.example.githubclient.ui.userslist

class UsersListContract {
    interface View {
        fun showLoginsAsString(logins: String)
    }
    interface Presenter {
        fun onAttach(view: View)
        fun onDetach()
        fun onViewCreated()
    }
}