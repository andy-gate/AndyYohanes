package com.andy.andyyohanes.data.network.response

import com.andy.andyyohanes.data.db.User

data class UserResponse (
    val total_count : Int?,
    val incomplete_results : Boolean?,
    val items: List<User>?
)