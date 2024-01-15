package com.sdv.kit.solvio.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sdv.kit.solvio.R
import com.sdv.kit.solvio.entity.GameLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LevelsRecyclerViewHolder(itemView: View) : ViewHolder(itemView) {
    private val mLevelBackgroundImageView: ImageView = itemView.findViewById(R.id.levelBackgroundImageView)
    private val mLevelNameTextView: TextView = itemView.findViewById(R.id.levelNameTextView)
    private val mLevelStarsTextView: TextView = itemView.findViewById(R.id.levelStarsTextView)
    private val mLevelDescriptionTextView: TextView = itemView.findViewById(R.id.levelDescriptionTextView)

    fun bind(gameLevel: GameLevel) {
        mLevelNameTextView.text = gameLevel.levelName
        mLevelStarsTextView.text = gameLevel.situationsCount.toString()
        mLevelDescriptionTextView.text = gameLevel.levelDescription
        loadImage(gameLevel)
    }

    private fun loadImage(gameLevel: GameLevel) = CoroutineScope(Dispatchers.Main).launch {
        Glide.with(itemView)
            .load(gameLevel.backgroundImageSrc)
            .placeholder(R.drawable.image_logo)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mLevelBackgroundImageView)
    }
}