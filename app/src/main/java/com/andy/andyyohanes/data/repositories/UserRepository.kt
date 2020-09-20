package com.andy.andyyohanes.data.repositories

import com.andy.andyyohanes.data.network.MyApi
import com.andy.andyyohanes.data.network.SafeApiRequest
import com.andy.andyyohanes.data.network.response.UserResponse

class UserRepository(
    private val api: MyApi
) : SafeApiRequest() {
    suspend fun searchUser(keyword: String) : UserResponse {
        return apiRequest { api.searchUser(keyword) }
    }
}