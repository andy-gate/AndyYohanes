package com.andy.andyyohanes.ui

import com.andy.andyyohanes.data.network.response.UserResponse

interface MainListener {
    fun onStarted()
    fun onSuccess(user: UserResponse)
    fun onSuccessLoadMore(user: UserResponse)
    fun onFailure(message: String)
}