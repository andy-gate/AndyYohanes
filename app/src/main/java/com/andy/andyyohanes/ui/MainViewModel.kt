package com.andy.andyyohanes.ui

import android.text.Editable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.andy.andyyohanes.data.repositories.UserRepository
import com.andy.andyyohanes.util.ApiException
import com.andy.andyyohanes.util.Coroutines
import com.andy.andyyohanes.util.NoInternetException
import com.andy.andyyohanes.util.lazyDeferred
import com.bumptech.glide.Glide
import android.text.TextWatcher



class MainViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var search = MutableLiveData<String>()

    var mainListener: MainListener? = null

//    private val searchObserver = Observer<String> {
//        onSearchChanged(it)
//    }

//    init {
//        search.observeForever(searchObserver)
//    }

    fun onSearchUser(keyword: String){
        Coroutines.main{
            try{
                val userResponse = repository.searchUser(keyword)
                userResponse.let {
                    mainListener?.onSuccess(it)
                    return@main
                }
            } catch (e: ApiException) {
                mainListener?.onFailure(e.message!!)
            } catch (e: NoInternetException){
                mainListener?.onFailure(e.message!!)
            }
        }
    }

//    fun onSearchChanged(keyword: String){
//        onSearchUser(keyword)
//    }
}