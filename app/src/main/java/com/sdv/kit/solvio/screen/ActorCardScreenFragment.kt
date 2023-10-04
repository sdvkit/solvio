package com.sdv.kit.solvio.screen

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
import com.sdv.kit.solvio.databinding.FragmentScreenActorCardBinding
import com.sdv.kit.solvio.entity.Situation
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituations
import com.sdv.kit.solvio.viewmodel.ActorCardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActorCardScreenFragment : Fragment() {
    private var mActorCardViewModel: ActorCardViewModel? = null
    private var mBinding: FragmentScreenActorCardBinding? = null
    private var currentSituationIndex = 0
    private lateinit var mGameLevelWithSituations: GameLevelWithSituations

    override fun onDestroyView() {
        super.onDestroyView()
        mActorCardViewModel = null
        mBinding = null
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
    }

    private fun configureViews() = with (mBinding!!) {
        val situation = mGameLevelWithSituations.situations[currentSituationIndex]
        loadActorImage(actorImageView, situation)
        actorNameTextView.text = situation.actorName
        situationDescription.text = situation.situationDescription
    }

    private fun loadActorImage(
        actorImageView: ImageView,
        situation: Situation
    ) = CoroutineScope(Dispatchers.Main).launch {
        Glide.with(requireContext())
            .load(situation.actorImageUrl)
            .placeholder(R.drawable.image_logo)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(actorImageView)
    }

    private fun configureViewModel() {
        mActorCardViewModel = ViewModelProvider(this)[ActorCardViewModel::class.java]
    }

    private fun initializeGameLevelWithSituations() {
        mGameLevelWithSituations = Gson().fromJson(
            requireArguments().getString(GAME_LEVEL_WITH_SITUATIONS_KEY),
            GameLevelWithSituations::class.java
        )
    }

    companion object {
        private const val GAME_LEVEL_WITH_SITUATIONS_KEY = "gameLevelWithSituations"

        fun newInstance(gameLevelWithSituations: GameLevelWithSituations): ActorCardScreenFragment {
            val actorCardScreenFragment = ActorCardScreenFragment()

            actorCardScreenFragment.arguments = Bundle().apply {
                putString(GAME_LEVEL_WITH_SITUATIONS_KEY, Gson().toJson(gameLevelWithSituations))
            }

            return actorCardScreenFragment
        }
    }
}