package com.cyrilpillai.userful.list.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.cyrilpillai.userful.R
import com.cyrilpillai.userful.list.view.adapter.UserAdapter
import com.cyrilpillai.userful.list.viewmodel.UsersListViewModel
import com.cyrilpillai.userful.list.viewmodel.UsersListViewModelFactory
import com.cyrilpillai.userful.networking.entity.Outcome
import com.cyrilpillai.userful.utils.Constants
import com.cyrilpillai.userful.utils.ViewStatus
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_users_list.*
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

    private var menuRefresh: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
        setupObservers()
        setupViews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_users_list, menu)
        menuRefresh = menu.findItem(R.id.menuRefresh)
        btnTryAgain.performClick()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuRefresh -> {
                viewModel.getUsers(forceUpdate = true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViews() {
        rvUsers.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvUsers.adapter = userAdapter
        btnTryAgain.setOnClickListener { viewModel.getUsers() }
    }

    private fun setupObservers() {
        viewModel.usersLiveData.observe(this, Observer {
            when (it) {
                is Outcome.Progress -> {
                    Log.d(Constants.APP_NAME, "Loading: ${it.loading}")
                    changeViewVisibilities(ViewStatus.LOADING)
                }
                is Outcome.Success -> {
                    Log.d(Constants.APP_NAME, "Success: ${it.data.size}")
                    userAdapter.setData(it.data)
                    changeViewVisibilities(ViewStatus.SUCCESS)
                }
                is Outcome.Failure -> {
                    Log.d(Constants.APP_NAME, "Failure: ${it.e.localizedMessage}")
                    changeViewVisibilities(ViewStatus.ERROR)
                }
            }
        })
    }

    private fun changeViewVisibilities(viewStatus: ViewStatus) {
        when (viewStatus) {
            ViewStatus.LOADING -> {
                menuRefresh?.isVisible = false
                loadingView.visibility = View.VISIBLE
                rvUsers.visibility = View.GONE
                groupError.visibility = View.GONE
            }
            ViewStatus.SUCCESS -> {
                menuRefresh?.isVisible = true
                loadingView.visibility = View.GONE
                rvUsers.visibility = View.VISIBLE
                groupError.visibility = View.GONE
            }
            ViewStatus.ERROR -> {
                menuRefresh?.isVisible = true
                loadingView.visibility = View.GONE
                rvUsers.visibility = View.GONE
                groupError.visibility = View.VISIBLE
            }
        }
    }
}
