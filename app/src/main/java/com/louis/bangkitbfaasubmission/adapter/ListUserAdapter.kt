package com.louis.bangkitbfaasubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.louis.bangkitbfaasubmission.databinding.ItemRowUserBinding
import com.louis.bangkitbfaasubmission.loadCircleImage
import com.louis.bangkitbfaasubmission.model.User

class ListUserAdapter (private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        with(holder.binding) {
            tvItemName.text = user.name
            tvItemCompany.text = user.company
            tvItemLocation.text = user.location
            ivItemAvatar.loadCircleImage(user.avatar)
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
    }

    override fun getItemCount() = listUser.size

    class ListViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}