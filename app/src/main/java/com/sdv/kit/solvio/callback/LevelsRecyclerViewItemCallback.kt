package com.sdv.kit.solvio.callback

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituations

class LevelsRecyclerViewItemCallback : ItemCallback<GameLevelWithSituations>() {
    override fun areItemsTheSame(
        oldItem: GameLevelWithSituations,
        newItem: GameLevelWithSituations
    ): Boolean = oldItem.gameLevel.levelName == newItem.gameLevel.levelName

    override fun areContentsTheSame(
        oldItem: GameLevelWithSituations,
        newItem: GameLevelWithSituations
    ): Boolean = oldItem.gameLevel == newItem.gameLevel

}