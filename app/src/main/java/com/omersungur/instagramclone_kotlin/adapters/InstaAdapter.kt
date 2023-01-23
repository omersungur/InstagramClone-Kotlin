package com.omersungur.instagramclone_kotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.omersungur.instagramclone_kotlin.databinding.RecyclerRowBinding
import com.omersungur.instagramclone_kotlin.model.Post
import com.squareup.picasso.Picasso

class InstaAdapter(private val postList : ArrayList<Post>) : RecyclerView.Adapter<InstaAdapter.InstaViewHolder>() {

    class InstaViewHolder(val binding : RecyclerRowBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstaViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return InstaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: InstaViewHolder, position: Int) {
        holder.binding.recyclerViewUserEmail.text = postList[position].userName
        holder.binding.recyclerViewCommentText.text = postList[position].comment
        Picasso.get().load(postList[position].downloadUrl).into(holder.binding.recyclerViewImageView)
    }
}