package com.sdv.kit.solvio.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sdv.kit.solvio.databinding.ViewCreateLevelDialogBinding
import com.sdv.kit.solvio.entity.GameLevel
import com.sdv.kit.solvio.viewmodel.CreateLevelBottomSheetViewModel

class CreateLevelBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var mBinding: ViewCreateLevelDialogBinding
    private var mViewModel: CreateLevelBottomSheetViewModel? = null

    override fun onDetach() {
        super.onDetach()
        mViewModel = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ViewCreateLevelDialogBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewModel()
        setClickListeners()
    }

    private fun configureViewModel() {
        mViewModel = ViewModelProvider(requireActivity())[CreateLevelBottomSheetViewModel::class.java]
    }

    private fun setClickListeners() = with (mBinding) {
        saveButton.setOnClickListener {
            if (levelNameEditText.text.isNotEmpty()
                && descriptionEditText.text.isNotEmpty()
                && imageUrlEditText.text.isNotEmpty()) {

                mViewModel!!.saveLevel(buildGameLevel())
                dismiss()
            }
        }
    }

    private fun buildGameLevel(): GameLevel = GameLevel.Builder()
        .levelName(mBinding.levelNameEditText.text.toString())
        .levelDescription(mBinding.descriptionEditText.text.toString())
        .backgroundImageUrl(mBinding.imageUrlEditText.text.toString())
        .build()
}