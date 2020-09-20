package com.andy.andyyohanes.ui

import androidx.lifecycle.LiveData
import com.andy.andyyohanes.data.network.response.UserResponse

interface MainListener {
    fun onStarted()
    fun onSuccess(user: UserResponse)
    fun onFailure(message: String)
}