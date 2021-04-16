package com.alexko.test.app.ui.pictures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexko.test.app.databinding.ListItemPictureBinding
import com.alexko.test.app.dc.PictureData
import com.squareup.picasso.Picasso

class PicturesListAdapted :
    ListAdapter<PictureData, PicturesListAdapted.PictureViewHolder>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PictureViewHolder(
            ListItemPictureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: PictureViewHolder) {
        super.onViewRecycled(holder)
        holder.cleanup()
    }

    class PictureViewHolder(private val binding: ListItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(picture: PictureData) {
            binding.apply {
                picture.downloadUrl?.let {
                    image.post {
                        Picasso.get().load(picture.downloadUrl)
                            .resize(image.measuredWidth, image.measuredHeight)
                            .centerCrop().into(image)
                    }
                }
                text.text = picture.author;
            }
        }

        fun cleanup() {
            Picasso.get().cancelRequest(binding.image)
            binding.image.setImageDrawable(null)
        }
    }

    class Diff : DiffUtil.ItemCallback<PictureData>() {
        override fun areItemsTheSame(oldItem: PictureData, newItem: PictureData) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PictureData, newItem: PictureData) =
            oldItem == newItem
    }
}