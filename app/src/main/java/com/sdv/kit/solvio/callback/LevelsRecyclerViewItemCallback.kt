package com.sdv.kit.solvio.callback

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.sdv.kit.solvio.entity.GameLevel

class LevelsRecyclerViewItemCallback : ItemCallback<GameLevel>() {
    override fun areItemsTheSame(oldItem: GameLevel, newItem: GameLevel): Boolean = oldItem.levelName == newItem.levelName
    override fun areContentsTheSame(oldItem: GameLevel, newItem: GameLevel): Boolean = oldItem == newItem
}