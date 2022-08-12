package com.example.codeexample.presentation.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codeexample.data.models.Post
import com.example.codeexample.databinding.ItemPostBinding

class Adapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<PostViewHolder>() {

    private val list = arrayListOf<Post>()

    private lateinit var binding: ItemPostBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, onClick)
    }

    override fun getItemCount(): Int = list.size

    fun addAll(posts: List<Post>) {
        list.clear()
        list.addAll(posts)
        notifyItemRangeChanged(0, posts.size)
    }
}

class PostViewHolder(
    private val binding: ItemPostBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Post, onClick: (Int) -> Unit) {
        binding.title = item.title
        binding.root.setOnClickListener { onClick(item.id) }
    }
}
