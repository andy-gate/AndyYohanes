package com.andy.andyyohanes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.andy.andyyohanes.databinding.ActivityMainBinding
import com.andy.andyyohanes.R
import com.andy.andyyohanes.data.db.User
import com.andy.andyyohanes.data.network.response.UserResponse
import com.andy.andyyohanes.util.Coroutines
import com.andy.andyyohanes.util.hide
import com.andy.andyyohanes.util.show
import com.andy.andyyohanes.util.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), MainListener, KodeinAware {

    override val kodein by kodein()

    private val factory : MainViewModelFactory by instance()

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProviders.of(this,factory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.mainListener = this
        et_search.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.onSearchUser(v.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    override fun onStarted() {
        pb_loading.show()
    }

    override fun onSuccess(user: UserResponse) {
        pb_loading.hide()
        initRecyclerView(user.items!!.toUserItem())
    }

    override fun onFailure(message: String) {
        toast(message)
    }

    private fun initRecyclerView(userItem: List<UserItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(userItem)
        }
        rv_results.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            ))
        }
    }

    private fun List<User>.toUserItem() : List<UserItem> {
        return this.map {
            UserItem(it)
        }
    }
}
