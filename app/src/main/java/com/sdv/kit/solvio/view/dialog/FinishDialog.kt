package com.sdv.kit.solvio.view.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.sdv.kit.solvio.R
import com.sdv.kit.solvio.databinding.ViewFinishDialogBinding
import com.sdv.kit.solvio.entity.Choice
import com.sdv.kit.solvio.view.ChoiceResultView

class FinishDialog : BottomSheetDialogFragment() {
    private lateinit var mBinding: ViewFinishDialogBinding
    private val mChoices: List<*> by lazy {
        Gson().fromJson(requireArguments()
            .getString(CHOICES_HISTORY_KEY), List::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ViewFinishDialogBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        setClickListeners()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        return dialog
    }

    @SuppressLint("SetTextI18n")
    private fun configureViews() = with (mBinding) {
        val starsCount = requireArguments().getInt(STARS_COUNT_KEY)
        resultsTextView.text = "Вы набрали $starsCount звёзд из ${mChoices.size}."

        mChoices.forEach {
            val choice = it as LinkedTreeMap<*, *>
            val choiceResultView = ChoiceResultView(requireContext(), null)
            choiceResultView.configure(
                (choice["situation"] as LinkedTreeMap<*, *>)["actorName"].toString(),
                (choice["situation"] as LinkedTreeMap<*, *>)["actorImageUrl"].toString(),
                (choice["actionIndex"] as Double).toInt() + 1,
                choice["isActionPositive"] as Boolean
            )
            historyLinearLayout.addView(choiceResultView)
        }
    }

    private fun setClickListeners() = with (mBinding) {
        backToMainMenuButton.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        private const val CHOICES_HISTORY_KEY = "choices"
        private const val STARS_COUNT_KEY = "starsCount"

        fun instance(choices: List<Choice>, starsCount: Int): FinishDialog {
            val actionResultDialog = FinishDialog()

            val args = Bundle().apply {
                putString(CHOICES_HISTORY_KEY, Gson().toJson(choices))
                putInt(STARS_COUNT_KEY, starsCount)
            }

            actionResultDialog.arguments = args
            return actionResultDialog
        }
    }
}