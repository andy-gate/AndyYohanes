package com.andy.andyyohanes.ui

import com.andy.andyyohanes.R
import com.andy.andyyohanes.data.db.User
import com.xwray.groupie.databinding.BindableItem
import com.andy.andyyohanes.databinding.ItemUserBinding

class UserItem(
    private val user: User
) : BindableItem<ItemUserBinding>(){
    override fun getLayout() = R.layout.item_user

    override fun bind(viewBinding: ItemUserBinding, position: Int) {
        viewBinding.setUser(user)
    }
}