package com.andy.andyyohanes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andy.andyyohanes.databinding.ActivityMainBinding
import com.andy.andyyohanes.R
import com.andy.andyyohanes.data.db.User
import com.andy.andyyohanes.data.network.response.UserResponse
import com.andy.andyyohanes.util.*
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

    private lateinit var layoutManagerRV: LinearLayoutManager

    private lateinit var mAdapter: GroupAdapter<ViewHolder>

    private var total_count = 0

    private var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProviders.of(this,factory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        mAdapter = GroupAdapter<ViewHolder>()
        layoutManagerRV = LinearLayoutManager(this)

        viewModel.mainListener = this
        et_search.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if(v.text.length==0)
                        toast("You must enter name of github user")
                    else
                        viewModel.onSearchUser()
                    true
                }
                else -> false
            }
        }
    }

    override fun onStarted() {
        pb_loading.show()
    }

    private fun onResponseReady(){
        pb_loading.hide()
        hideKeyboard(this)
        et_search.clearFocus()
    }

    override fun onSuccess(user: UserResponse) {
        onResponseReady()
        if(user.items!!.isEmpty()){
            ll_no_data.visibility = View.VISIBLE
            rv_results.visibility = View.GONE
        } else {
            total_count = user.total_count!!
            ll_no_data.visibility = View.GONE
            rv_results.visibility = View.VISIBLE
            initRecyclerView(user.items!!.toUserItem())
        }
    }

    override fun onSuccessLoadMore(user: UserResponse) {
        onResponseReady()
        mAdapter.apply {
            addAll(user.items!!.toUserItem())
        }
    }

    override fun onFailure(message: String) {
        onResponseReady()
        toast(message)
    }

    private fun initRecyclerView(userItem: List<UserItem>) {
        mAdapter.clear()
        mAdapter.apply {
            addAll(userItem)
        }

        rv_results.apply {
            layoutManager = layoutManagerRV
            setHasFixedSize(true)
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            ))
            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                        if(layoutManagerRV.findLastCompletelyVisibleItemPosition()+1 == layoutManagerRV.itemCount &&
                            layoutManagerRV.itemCount < total_count &&
                            layoutManagerRV.itemCount < 1000)
                        {
                            viewModel.onLoadMore()
                        }
                }
            })
        }
    }

    private fun List<User>.toUserItem() : List<UserItem> {
        return this.map {
            UserItem(it)
        }
    }
}
