package com.andy.andyyohanes.data.db

import androidx.room.Entity

@Entity
data class User(
    var id : Int? = null,
    var login: String? = null,
    var avatar_url: String? = null
)