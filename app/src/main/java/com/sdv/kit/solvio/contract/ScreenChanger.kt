package com.sdv.kit.solvio.contract

import androidx.fragment.app.Fragment

interface ScreenChanger {
    fun changeScreen(screenFragment: Fragment)
    fun openScreen(screenFragment: Fragment)
}