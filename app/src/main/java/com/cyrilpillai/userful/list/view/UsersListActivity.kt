package com.cyrilpillai.userful.list.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import com.cyrilpillai.userful.R
import com.cyrilpillai.userful.list.view.adapter.UserAdapter
import com.cyrilpillai.userful.list.viewmodel.UsersListViewModel
import com.cyrilpillai.userful.list.viewmodel.UsersListViewModelFactory
import com.cyrilpillai.userful.networking.entity.Outcome
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_users_list.*
import retrofit2.Retrofit
import javax.inject.Inject

class UsersListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: UsersListViewModelFactory

    @Inject
    lateinit var userAdapter: UserAdapter

    private val viewModel: UsersListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(UsersListViewModel::class.java)
    }

    private val context: Context by lazy { this@UsersListActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        rvUsers.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvUsers.adapter = userAdapter

        viewModel.usersLiveData.observe(this, Observer {
            when (it) {
                is Outcome.Progress -> {
                    Log.d("Issue", "Loading: ${it.loading}")
                }
                is Outcome.Success -> {
                    Log.d("Issue", "Success: ${it.data.size}")
                    userAdapter.setData(it.data)
                }
                is Outcome.Failure -> {
                    Log.d("Issue", "Failure: ${it.e.localizedMessage}")
                }
            }
        })
        viewModel.getUsers(true)
    }
}
