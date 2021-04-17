package com.alexko.test.app.ui.pictures

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexko.test.app.databinding.ListItemPictureBinding
import com.alexko.test.app.dc.PictureData
import com.squareup.picasso.Picasso

class PicturesListAdapted(private val callback: Callback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val startLoadOffset: Int = 3
    private var loadRequested = false

    private val picturesList = ArrayList<PictureData>()

    fun submit(pictures: List<PictureData>) {
        loadRequested = false
        picturesList.addAll(pictures)
        notifyItemRangeInserted(picturesList.size - pictures.size, pictures.size)
    }

    private fun requestLoad() {
        if (loadRequested)
            return
        loadRequested = true
        callback.requestMore()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == ViewHolderType.Picture.ordinal)
            PictureViewHolder(
                ListItemPictureBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            ProgressBarViewHolder(parent.context)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position > itemCount - startLoadOffset)
            requestLoad()

        if (holder is PictureViewHolder)
            holder.bind(picturesList[position])
    }

    override fun getItemCount() = picturesList.size + 1

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        (holder as? PictureViewHolder)?.cleanup()
    }

    override fun getItemViewType(position: Int) =
        (if (position == picturesList.size) ViewHolderType.ProgressBar else ViewHolderType.Picture).ordinal

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

    class ProgressBarViewHolder(context: Context) :
        RecyclerView.ViewHolder(ProgressBar(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1.0f
                gravity = Gravity.TOP
            }
        })

    enum class ViewHolderType {
        Picture,
        ProgressBar
    }

    interface Callback {
        fun requestMore()
    }
}