package com.sdv.kit.solvio.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sdv.kit.solvio.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChoiceResultView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_choice_item, this, true)
    }

    fun configure(
        actorName: String,
        actorImageUrl: String,
        choiceIndex: Int,
        isChoicePositive: Boolean
    ) {
        loadImage(actorImageUrl)
        findViewById<TextView>(R.id.actorNameTextView).text = actorName
        val choiceIndexTextViewColor = if (isChoicePositive) R.color.green else R.color.red
        val choiceIndexTextView = findViewById<TextView>(R.id.choiceIndexTextView)
        choiceIndexTextView.text = choiceIndex.toString()
        choiceIndexTextView.setTextColor(ContextCompat.getColor(context, choiceIndexTextViewColor))
    }

    private fun loadImage(actorImageUrl: String) = CoroutineScope(Dispatchers.Main).launch {
        Glide.with(context)
            .load(actorImageUrl)
            .placeholder(R.drawable.image_logo)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(findViewById(R.id.actorImageView))
    }
}