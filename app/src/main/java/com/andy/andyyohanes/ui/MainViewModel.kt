package com.andy.andyyohanes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andy.andyyohanes.data.repositories.UserRepository
import com.andy.andyyohanes.util.ApiException
import com.andy.andyyohanes.util.Coroutines
import com.andy.andyyohanes.util.NoInternetException


class MainViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var search = MutableLiveData<String>()

    var mainListener: MainListener? = null

    var page = 1

    lateinit var keyword : String

    fun onSearchUser(){
        page=1
        keyword = search.value.toString()
        mainListener?.onStarted()
        Coroutines.main{
            try{
                val userResponse = repository.searchUser(keyword, page)
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

    fun onLoadMore(){
        mainListener?.onStarted()
        Coroutines.main{
            try{
                val userResponse = repository.searchUser(keyword, page+1)
                userResponse.let {
                    mainListener?.onSuccessLoadMore(it)
                    page = page+1
                    return@main
                }
            } catch (e: ApiException) {
                mainListener?.onFailure(e.message!!)
            } catch (e: NoInternetException){
                mainListener?.onFailure(e.message!!)
            }
        }
    }
}