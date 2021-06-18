package com.example.githubtrendingrepo.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrendingrepo.databinding.ItemsGithubListBinding
import com.example.githubtrendingrepo.room.entity.GitHubDataModel

class GithubPagingAdapter() : PagingDataAdapter<GitHubDataModel, GithubPagingAdapter.ViewHolder>(diffCallback = object :
        DiffUtil.ItemCallback<GitHubDataModel>() {

        override fun areItemsTheSame(oldItem: GitHubDataModel, newItem: GitHubDataModel): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: GitHubDataModel, newItem: GitHubDataModel): Boolean {
            return oldItem == newItem
        }

    }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemsGithubListBinding.inflate(LayoutInflater.from(parent.context)),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
//        holder.itemView.setOnClickListener {
//            data?.let { it1 -> listener.redirectToDetailScreen(position, it1) }
//        }
    }

    class ViewHolder(private val binding: ItemsGithubListBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gitHubDataModel: GitHubDataModel?) {
            with(binding) {
                gitHubDataModel?.run {
                    textViewFullName.text = name
                    textViewId.text = id
                    textViewUrl.text = html_url
                    textViewName.text = full_name
                }
            }

        }
    }
}








