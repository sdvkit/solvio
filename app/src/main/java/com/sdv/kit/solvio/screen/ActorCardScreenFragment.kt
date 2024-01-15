package com.sdv.kit.solvio.screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.sdv.kit.solvio.R
import com.sdv.kit.solvio.contract.ScreenChanger
import com.sdv.kit.solvio.databinding.FragmentScreenActorCardBinding
import com.sdv.kit.solvio.entity.Choice
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituationsAndActions
import com.sdv.kit.solvio.view.dialog.ActionResultDialog
import com.sdv.kit.solvio.view.dialog.FinishDialog
import com.sdv.kit.solvio.viewmodel.ActorCardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActorCardScreenFragment : Fragment() {
    private var mActorCardViewModel: ActorCardViewModel? = null
    private var mBinding: FragmentScreenActorCardBinding? = null
    private var mScreenChanger: ScreenChanger? = null
    private var mCurrentSituationIndex = 0
    private var mCurrentStarsCount = 0
    private var mChoicesHistoryList = mutableListOf<Choice>()
    private lateinit var mGameLevelWithSituationsAndActions: GameLevelWithSituationsAndActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mScreenChanger = context as ScreenChanger
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mActorCardViewModel = null
        mBinding = null
        mScreenChanger = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentScreenActorCardBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeGameLevelWithSituations()
        configureViewModel()
        configureViews()
        setClickListeners()
    }

    private fun setClickListeners() = with (mBinding!!) {
        backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        actionButton1.setOnClickListener {
            showActionResult(0)
            checkAndIncrementStarsCount(0)
            changeNextScreen()
        }

        actionButton2.setOnClickListener {
            showActionResult(1)
            checkAndIncrementStarsCount(1)
            changeNextScreen()
        }
    }

    private fun changeNextScreen() {
        if (mCurrentSituationIndex < mGameLevelWithSituationsAndActions.situations.size - 1) {
            configureCardByNextSituation()
        }
    }

    private fun configureCardByNextSituation() {
        mCurrentSituationIndex++
        configureViews()
    }

    private fun checkAndIncrementStarsCount(actionIndex: Int) {
        val situationWithActions = mGameLevelWithSituationsAndActions.situations[mCurrentSituationIndex]
        val action = situationWithActions.actions[actionIndex]

        if (action.isPositive) {
            mCurrentStarsCount++
            updateStarsCount()
        }

        mChoicesHistoryList.add(Choice(situationWithActions.situation, actionIndex, action.isPositive))
    }

    @SuppressLint("SetTextI18n")
    private fun updateStarsCount() {
        mBinding!!.starsCountTextView.text = "${mCurrentStarsCount}/${mGameLevelWithSituationsAndActions.situations.size}"
    }

    private fun showActionResult(actionIndex: Int) {
        val situationWithActions = mGameLevelWithSituationsAndActions.situations[mCurrentSituationIndex]

        if (mCurrentSituationIndex < mGameLevelWithSituationsAndActions.situations.size - 1) {
            ActionResultDialog(situationWithActions.actions[actionIndex])
                .show(parentFragmentManager, null)
        } else {
            ActionResultDialog(situationWithActions.actions[actionIndex]) {
                mScreenChanger!!.changeScreen(MainMenuScreenFragment())
                FinishDialog.instance(mChoicesHistoryList, mCurrentStarsCount)
                    .show(parentFragmentManager, null)
            }.show(parentFragmentManager, null)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun configureViews() = with (mBinding!!) {
        val situationWithActions = mGameLevelWithSituationsAndActions.situations[mCurrentSituationIndex]
        loadActorImage(actorImageView, situationWithActions.situation.actorImageUrl)
        updateStarsCount()

        actorNameTextView.text = situationWithActions.situation.actorName
        situationDescription.text = situationWithActions.situation.situationDescription
        actionButton1.text = situationWithActions.actions[0].actionDescription
        actionButton2.text = situationWithActions.actions[1].actionDescription
    }

    private fun loadActorImage(
        actorImageView: ImageView,
        actorImageUrl: String
    ) = CoroutineScope(Dispatchers.Main).launch {
        Glide.with(requireContext())
            .load(actorImageUrl)
            .placeholder(R.drawable.image_logo)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(actorImageView)
    }

    private fun configureViewModel() {
        mActorCardViewModel = ViewModelProvider(this)[ActorCardViewModel::class.java]
    }

    private fun initializeGameLevelWithSituations() {
        mGameLevelWithSituationsAndActions = Gson().fromJson(
            requireArguments().getString(GAME_LEVEL_WITH_SITUATIONS_KEY),
            GameLevelWithSituationsAndActions::class.java
        )
    }

    companion object {
        private const val GAME_LEVEL_WITH_SITUATIONS_KEY = "gameLevelWithSituations"

        fun newInstance(gameLevelWithSituationsAndActions: GameLevelWithSituationsAndActions): ActorCardScreenFragment {
            val actorCardScreenFragment = ActorCardScreenFragment()

            actorCardScreenFragment.arguments = Bundle().apply {
                putString(GAME_LEVEL_WITH_SITUATIONS_KEY, Gson().toJson(gameLevelWithSituationsAndActions))
            }

            return actorCardScreenFragment
        }
    }
}