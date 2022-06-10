package com.amosh.feature.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.amosh.base.BaseRecyclerAdapter
import com.amosh.base.BaseViewHolder
import com.amosh.feature.R
import com.amosh.feature.databinding.RowImageSliderBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageSliderViewPagerAdapter : BaseRecyclerAdapter<String, RowImageSliderBinding, ImageSliderViewPagerAdapter.ViewHolder>(StringItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowImageSliderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding = binding)
    }

    inner class ViewHolder(
        private val binding: RowImageSliderBinding,
    ) : BaseViewHolder<String, RowImageSliderBinding>(binding) {

        override fun bind() {

            getRowItem()?.let {
                with(binding) {
                    ivImage.apply {
                        Glide.with(this)
                            .load(it)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_app_logo)
                            .placeholder(R.drawable.ic_app_logo)
                            .into(this)
                    }
                }
            }
        }
    }
}

class StringItemDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}