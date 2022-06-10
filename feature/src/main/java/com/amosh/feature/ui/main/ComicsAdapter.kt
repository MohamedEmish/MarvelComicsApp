package com.amosh.feature.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.amosh.base.BaseRecyclerAdapter
import com.amosh.base.BaseViewHolder
import com.amosh.common.Constants
import com.amosh.feature.R
import com.amosh.feature.databinding.RowComicsItemLayoutBinding
import com.amosh.feature.model.ComicsUiModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.shape.CornerFamily

/**
 * Adapter class for RecyclerView
 */
class ComicsAdapter constructor(
    private val clickFunc: ((ComicsUiModel?) -> Unit)? = null,
    private val fetchNext: (() -> Unit)? = null,
) : BaseRecyclerAdapter<ComicsUiModel, RowComicsItemLayoutBinding, ComicsAdapter.ComicsViewHolder>(
    ComicsItemDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val binding = RowComicsItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ComicsViewHolder(binding = binding, click = clickFunc, fetchNext = fetchNext)
    }

    inner class ComicsViewHolder(
        private val binding: RowComicsItemLayoutBinding,
        private val click: ((ComicsUiModel?) -> Unit)? = null,
        private val fetchNext: (() -> Unit)? = null,
    ) : BaseViewHolder<ComicsUiModel, RowComicsItemLayoutBinding>(binding) {

        override fun bind() {
            if ((currentList.size - 1) - absoluteAdapterPosition == Constants.LIST_BOTTOM_OFFSET)
                fetchNext?.invoke()

            getRowItem()?.let {
                with(binding) {
                    root.setOnClickListener {
                        click?.invoke(getRowItem())
                    }

                    tvTitle.text = it.title

                    ivImage.apply {
                        shapeAppearanceModel = binding.ivImage.shapeAppearanceModel
                            .toBuilder()
                            .setTopRightCorner(CornerFamily.ROUNDED, 36f)
                            .setTopLeftCorner(CornerFamily.ROUNDED, 36f)
                            .build()

                        Glide.with(this)
                            .load(it.thumbnail)
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

class ComicsItemDiffUtil : DiffUtil.ItemCallback<ComicsUiModel>() {
    override fun areItemsTheSame(oldItem: ComicsUiModel, newItem: ComicsUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ComicsUiModel, newItem: ComicsUiModel): Boolean {
        return oldItem == newItem
    }
}