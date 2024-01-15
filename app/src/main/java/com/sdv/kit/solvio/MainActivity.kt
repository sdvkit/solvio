package com.sdv.kit.solvio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.sdv.kit.solvio.contract.ScreenChanger
import com.sdv.kit.solvio.databinding.ActivityMainBinding
import com.sdv.kit.solvio.screen.AuthScreenFragment

class MainActivity : AppCompatActivity(), ScreenChanger {
    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        changeScreen(AuthScreenFragment())
    }

    override fun changeScreen(screenFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.screensFragmentContainerView, screenFragment)
            .commit()
    }

    override fun openScreen(screenFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.screensFragmentContainerView, screenFragment)
            .addToBackStack(null)
            .commit()
    }
}