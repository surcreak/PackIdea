package com.surcreak.packidea.homepage.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.surcreak.packidea.common.data.UnsplashPhoto
import com.surcreak.packidea.homepage.R
import com.surcreak.packidea.homepage.databinding.HomepageListItemPhotoBinding
import com.surcreak.packidea.homepage.databinding.HomepageRecommendItem1Binding
import kotlinx.android.synthetic.main.homepage_list_item_photo.view.*

class RecommendAdapter : PagingDataAdapter<UnsplashPhoto, RecommendAdapter.GalleryViewHolder>(GalleryDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {

        return GalleryViewHolder(
            HomepageListItemPhotoBinding.bind(LayoutInflater.from(parent.context)
                .inflate(R.layout.homepage_list_item_photo, null)))
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }
    }


    class GalleryViewHolder(val binding: HomepageListItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UnsplashPhoto) {
            Glide.with(itemView.context)
                .asDrawable()
                .error(R.drawable.error)
                .load(item.urls.small)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.plantPhoto)
        }
    }

}

private class GalleryDiffCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
    override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        return oldItem == newItem
    }
}