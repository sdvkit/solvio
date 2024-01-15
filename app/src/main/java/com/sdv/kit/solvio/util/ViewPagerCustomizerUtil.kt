package com.sdv.kit.solvio.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sdv.kit.solvio.R

class ViewPagerCustomizerUtil private constructor() {
    companion object {
        fun customizeViewPager(viewPager2: ViewPager2, resources: Resources) {
            viewPager2.offscreenPageLimit = 1

            val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
            val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)

            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = - (nextItemVisiblePx + currentItemHorizontalMarginPx) * position
                page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            }

            val itemDecoration = HorizontalMarginItemDecoration(
                viewPager2.context,
                R.dimen.viewpager_current_item_horizontal_margin
            )

            viewPager2.setPageTransformer(pageTransformer)
            viewPager2.addItemDecoration(itemDecoration)
        }
    }

    private class HorizontalMarginItemDecoration(
        context: Context,
        @DimenRes horizontalMarginInDp: Int
    ) : RecyclerView.ItemDecoration() {

        private val mHorizontalMarginInPx: Int by lazy {
            context.resources.getDimension(horizontalMarginInDp).toInt()
        }

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = mHorizontalMarginInPx
            outRect.left = mHorizontalMarginInPx
        }
    }
}