package com.flowz.agromailjobtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.flowz.paybackjobapp.R
import com.flowz.paybackjobapp.databinding.ImageListItemBinding
import com.flowz.paybackjobapp.models.Hit


class ImagesAdapter  (val listener: RowClickListener)  :ListAdapter<Hit, ImagesAdapter.ImageViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ImageViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)

        return ImageViewHolder(ImageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                userName.text = currentItem.user
                imageTags.text = currentItem.tags

                val imageLink = currentItem?.previewURL

                imageThumbail.load(imageLink){
                    error(R.drawable.ic_baseline_error_24)
                    placeholder(R.drawable.ic_baseline_search_24)
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }

    inner class ImageViewHolder(val binding: ImageListItemBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val item = getItem(adapterPosition)
                listener.onItemClickListener(item)
            }
        }
    }


    interface RowClickListener{
        fun onItemClickListener(hit: Hit)

    }

}