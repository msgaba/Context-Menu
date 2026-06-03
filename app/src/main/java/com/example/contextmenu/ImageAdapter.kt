package com.example.contextmenu

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contextmenu.databinding.ListItemImageBinding


/**
 * Created by Ankita Gaba on 10/11/25.
 */
class ImageAdapter(private val activity: Activity, private val imageList: List<Pair<Int, Boolean>>, private val listener: ImageClickListener) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ImageViewHolder(
        ListItemImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(
        holder: ImageViewHolder,
        position: Int
    ) {
        holder.bind(imageList[position], position)
    }

    override fun getItemCount() = imageList.size

    inner class ImageViewHolder(private val binding: ListItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pair<Int, Boolean>, pos: Int) {
            binding.apply {
                val params = image.layoutParams
                val displayMetrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                val screenWidth = displayMetrics.widthPixels
                val paddingHorizontal = (image.context.resources.displayMetrics.density * 6).toInt()
                val width = (screenWidth - (2 * paddingHorizontal))/SPAN_COUNT
                params.width = width
                params.height = width
                image.layoutParams = params
                val paddingVertical = image.paddingTop
                image.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
                image.setImageResource(item.first)
                like.setImageResource(if(item.second) R.drawable.ic_heart_filled else R.drawable.ic_heart)
                image.setOnLongClickListener {
                    listener.onLongPressImage(image, pos)
                    return@setOnLongClickListener true
                }
            }
        }
    }
}

interface ImageClickListener {
    fun onLongPressImage(view: View, pos: Int)
}