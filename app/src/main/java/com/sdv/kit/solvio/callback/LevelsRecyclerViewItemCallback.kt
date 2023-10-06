package com.sdv.kit.solvio.callback

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituationsAndActions

class LevelsRecyclerViewItemCallback : ItemCallback<GameLevelWithSituationsAndActions>() {
    override fun areItemsTheSame(
        oldItem: GameLevelWithSituationsAndActions,
        newItem: GameLevelWithSituationsAndActions
    ): Boolean = oldItem.gameLevel.levelName == newItem.gameLevel.levelName

    override fun areContentsTheSame(
        oldItem: GameLevelWithSituationsAndActions,
        newItem: GameLevelWithSituationsAndActions
    ): Boolean = oldItem.gameLevel == newItem.gameLevel

}