package com.louis.bangkitbfaasubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
<<<<<<< Updated upstream
import com.louis.bangkitbfaasubmission.databinding.ItemRowUserBinding
import com.louis.bangkitbfaasubmission.loadCircleImage
import com.louis.bangkitbfaasubmission.model.User
=======
import com.louis.bangkitbfaasubmission.data.remote.response.UserItem
import com.louis.bangkitbfaasubmission.databinding.ItemListUserBinding
import com.louis.bangkitbfaasubmission.utils.Helper.loadCircleImage
>>>>>>> Stashed changes

class ListUserAdapter (private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        with(holder.binding) {
<<<<<<< Updated upstream
            tvItemName.text = user.name
            tvItemCompany.text = user.company
            tvItemLocation.text = user.location
            ivItemAvatar.loadCircleImage(user.avatar)
=======
            tvItemName.text = user.login
            ivItemAvatar.loadCircleImage(user.avatarUrl)
>>>>>>> Stashed changes
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
    }

    override fun getItemCount() = listUser.size

    class ListViewHolder(val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}