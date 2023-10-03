package com.sdv.kit.solvio.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.sdv.kit.solvio.adapter.LevelsRecyclerViewAdapter
import com.sdv.kit.solvio.databinding.FragmentScreenMainMenuBinding
import com.sdv.kit.solvio.entity.GameLevel
import com.sdv.kit.solvio.util.ViewPagerCustomizerUtil
import com.sdv.kit.solvio.viewmodel.MainViewModel

class MainMenuScreenFragment : Fragment() {
    private var mBinding: FragmentScreenMainMenuBinding? = null
    private var mMainViewModel: MainViewModel? = null
    private var mLevelsRecyclerViewAdapter: LevelsRecyclerViewAdapter? = null
    private var mCurrentGameLevels: List<GameLevel> = listOf()
    private var mSelectedGameLevel: GameLevel? = null

    override fun onDestroy() {
        super.onDestroy()
        mMainViewModel = null
        mLevelsRecyclerViewAdapter = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentScreenMainMenuBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewPager()
        configureViewModel()
    }

    private fun configureViewPager() {
        mLevelsRecyclerViewAdapter = LevelsRecyclerViewAdapter(requireContext())
        mBinding!!.levelsViewPager.adapter = mLevelsRecyclerViewAdapter

        mBinding!!.levelsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mSelectedGameLevel = mCurrentGameLevels[position]
            }
        })

        ViewPagerCustomizerUtil.customizeViewPager(mBinding!!.levelsViewPager, resources)
    }

    private fun configureViewModel() {
        mMainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observeViewModels()
        mMainViewModel!!.getLevels()
    }

    private fun observeViewModels() {
        mMainViewModel!!.levels.observe(viewLifecycleOwner) { levels ->
            mCurrentGameLevels = levels
            mLevelsRecyclerViewAdapter!!.submitList(levels)
        }
    }
}