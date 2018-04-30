package com.cyrilpillai.userful.list.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.cyrilpillai.userful.R
import com.cyrilpillai.userful.list.viewmodel.UsersListViewModel
import com.cyrilpillai.userful.list.viewmodel.UsersListViewModelFactory
import com.cyrilpillai.userful.networking.entity.Outcome
import dagger.android.AndroidInjection
import javax.inject.Inject

class UsersListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: UsersListViewModelFactory

    private val viewModel: UsersListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(UsersListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
        viewModel.usersLiveData.observe(this, Observer {
            when (it) {
                is Outcome.Progress -> {
                    Log.d("Issue", "Loading: ${it.loading}")
                }
                is Outcome.Success -> {
                    Log.d("Issue", "Success: ${it.data.size}")
                }
                is Outcome.Failure -> {
                    Log.d("Issue", "Failure: ${it.e.localizedMessage}")
                }
            }
        })
        viewModel.getUsers(true)
    }
}
