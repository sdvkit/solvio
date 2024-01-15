package com.sdv.kit.solvio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sdv.kit.solvio.R
import com.sdv.kit.solvio.callback.LevelsRecyclerViewItemCallback
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituationsAndActions
import com.sdv.kit.solvio.viewholder.LevelsRecyclerViewHolder

class LevelsRecyclerViewAdapter(context: Context) : ListAdapter<GameLevelWithSituationsAndActions, LevelsRecyclerViewHolder>(LevelsRecyclerViewItemCallback()) {
    private val mInflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelsRecyclerViewHolder {
        val itemView = mInflater.inflate(R.layout.view_level_item, parent, false)
        return LevelsRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LevelsRecyclerViewHolder, position: Int) =
        holder.bind(getItem(position).gameLevel)
}