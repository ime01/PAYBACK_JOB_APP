package com.flowz.agromailjobtask.adapter

import androidx.recyclerview.widget.DiffUtil
import com.flowz.paybackjobapp.models.Hit
import com.flowz.paybackjobapp.models.ImageResponse


class ImageDiffCallback : DiffUtil.ItemCallback<Hit>(){
    override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
        return oldItem == newItem
    }
}