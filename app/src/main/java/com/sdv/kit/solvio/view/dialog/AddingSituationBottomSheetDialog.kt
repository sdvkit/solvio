package com.sdv.kit.solvio.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sdv.kit.solvio.databinding.ViewAddSituationDialogBinding
import com.sdv.kit.solvio.entity.Action
import com.sdv.kit.solvio.entity.GameLevel
import com.sdv.kit.solvio.entity.Indexes
import com.sdv.kit.solvio.entity.Situation
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituationsAndActions
import com.sdv.kit.solvio.entity.relation.SituationWithActions
import com.sdv.kit.solvio.viewmodel.AddingSituationBottomSheetViewModel

class AddingSituationBottomSheetDialog(private val gameLevel: GameLevel) : BottomSheetDialogFragment() {
    private lateinit var mBinding: ViewAddSituationDialogBinding
    private var mViewModel: AddingSituationBottomSheetViewModel? = null
    private lateinit var currentIndexes: Indexes

    override fun onDetach() {
        super.onDetach()
        mViewModel = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ViewAddSituationDialogBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewModel()
        configureViews()
        setClickListeners()
    }

    private fun configureViews() = with (mBinding) {
        levelTextView.text = gameLevel.levelName
    }

    private fun configureViewModel() {
        mViewModel = ViewModelProvider(requireActivity())[AddingSituationBottomSheetViewModel::class.java]
        mViewModel!!.indexes.observe(requireActivity()) { currentIndexes = it }
        mViewModel!!.getFirebaseIndexes()
    }

    private fun setClickListeners() = with (mBinding) {
        saveButton.setOnClickListener {
            val situationWithActions = SituationWithActions(buildSituation(), buildActions())
            mViewModel!!.saveSituationWithActions(GameLevelWithSituationsAndActions(gameLevel, listOf(situationWithActions)))
            dismiss()
        }
    }

    private fun buildActions(): List<Action> {
        val actions = mutableListOf<Action>()

        actions.add(Action.Builder()
            .actionId(currentIndexes.nextActionId)
            .actionResult(mBinding.firstActionResultEditText.text.toString())
            .actionResultImageUrl(mBinding.firstActionResultImageUrlEditText.text.toString())
            .actionDescription(mBinding.firstActionDescriptionEditText.text.toString())
            .isPositive(mBinding.firstActionIsPositiveCheckBox.isChecked)
            .build()
        )

        actions.add(Action.Builder()
            .actionId(currentIndexes.nextActionId + 1)
            .actionResult(mBinding.secondActionResultEditText.text.toString())
            .actionResultImageUrl(mBinding.secondActionResultImageUrlEditText.text.toString())
            .actionDescription(mBinding.secondActionDescriptionEditText.text.toString())
            .isPositive(mBinding.secondActionIsPositiveCheckBox.isChecked)
            .build())

        return actions
    }

    private fun buildSituation() = Situation.Builder()
        .situationId(currentIndexes.nextSituationId)
        .situationDescription(mBinding.situationDescriptionEditText.text.toString())
        .actorName(mBinding.actorNameEditText.text.toString())
        .actorImageUrl(mBinding.actorImageUrlEditText.text.toString())
        .build()
}