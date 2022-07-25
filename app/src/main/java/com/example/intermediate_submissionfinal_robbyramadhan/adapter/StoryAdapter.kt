package com.example.intermediate_submissionfinal_robbyramadhan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.intermediate_submissionfinal_robbyramadhan.databinding.ListItemStoryBinding
import com.example.intermediate_submissionfinal_robbyramadhan.fragment.StoryFragmentDirections
import com.example.intermediate_submissionfinal_robbyramadhan.model.StoryModel

class StoryAdapter : PagingDataAdapter<StoryModel, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private var binding: ListItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: StoryModel) {
            Glide.with(binding.root)
                .load(story.imageUrl)
                .signature(ObjectKey(story.imageUrl ?: story.id))
                .into(binding.listPhoto)

            binding.apply {
                listName.text = story.name
                listDescription.text = story.description
                root.setOnClickListener {
                    Navigation.findNavController(root).navigate(
                        StoryFragmentDirections.actionStoryFragmentToStoryDetailFragment(
                            story
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            ListItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        story?.let { holder.bind(it) }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<StoryModel>() {
                override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean =
                    oldItem.id == newItem.id
            }
    }


}