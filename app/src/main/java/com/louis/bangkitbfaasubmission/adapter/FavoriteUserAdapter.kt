package com.louis.bangkitbfaasubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.louis.bangkitbfaasubmission.data.local.entity.UserFavoriteEntity
import com.louis.bangkitbfaasubmission.databinding.ItemFavoriteBinding
import com.louis.bangkitbfaasubmission.utils.Helper.loadCircleImage
import java.lang.StringBuilder

class FavoriteUserAdapter (private val listUser: List<UserFavoriteEntity>) : RecyclerView.Adapter<FavoriteUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        with(holder.binding) {
            tvUsername.text = user.username
            tvName.text = user.name
            ivItem.loadCircleImage(user.avatarUrl)
            tvRepo.text = StringBuilder("${user.publicRepos}").append(" repo")
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
    }

    override fun getItemCount() = listUser.size

    class ListViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: UserFavoriteEntity)
    }
}