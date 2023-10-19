package com.sdv.kit.solvio.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.sdv.kit.solvio.adapter.LevelsRecyclerViewAdapter
import com.sdv.kit.solvio.contract.ScreenChanger
import com.sdv.kit.solvio.databinding.FragmentScreenMainMenuBinding
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituationsAndActions
import com.sdv.kit.solvio.util.ViewPagerCustomizerUtil
import com.sdv.kit.solvio.view.dialog.InfoBottomSheetDialog
import com.sdv.kit.solvio.view.dialog.SettingsBottomSheetDialog
import com.sdv.kit.solvio.viewmodel.MainViewModel

class MainMenuScreenFragment : Fragment() {
    private var mBinding: FragmentScreenMainMenuBinding? = null
    private var mMainViewModel: MainViewModel? = null
    private var mLevelsRecyclerViewAdapter: LevelsRecyclerViewAdapter? = null
    private var mCurrentGameLevels: List<GameLevelWithSituationsAndActions> = listOf()
    private var mSelectedGameLevel: GameLevelWithSituationsAndActions? = null
    private var mScreenChanger: ScreenChanger? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mScreenChanger = context as ScreenChanger
    }

    override fun onDestroy() {
        super.onDestroy()
        mMainViewModel = null
        mLevelsRecyclerViewAdapter = null
        mScreenChanger = null
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
        setClickListeners()
    }

    private fun setClickListeners() {
        mBinding!!.playButton.setOnClickListener {
            if (mSelectedGameLevel!!.situations.isNotEmpty()) {
                mScreenChanger?.openScreen(ActorCardScreenFragment.newInstance(mSelectedGameLevel!!))
            }
        }

        mBinding!!.settingsButton.setOnClickListener {
            SettingsBottomSheetDialog(mSelectedGameLevel?.gameLevel!!).show(childFragmentManager, null)
        }

        mBinding!!.infoButton.setOnClickListener {
            InfoBottomSheetDialog().show(childFragmentManager, null)
        }
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
        observeViewModel()
        mMainViewModel!!.getLevels()
    }

    private fun observeViewModel() {
        mMainViewModel!!.levels.observe(viewLifecycleOwner) { gameLevelsWithSituations ->
            mCurrentGameLevels = gameLevelsWithSituations
            mLevelsRecyclerViewAdapter!!.submitList(gameLevelsWithSituations)
        }
    }
}