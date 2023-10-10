package com.sdv.kit.solvio.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sdv.kit.solvio.databinding.ViewSettingsDialogBinding
import com.sdv.kit.solvio.entity.GameLevel
import com.sdv.kit.solvio.viewmodel.SettingsBottomSheetViewModel

class SettingsBottomSheetDialog(private val gameLevel: GameLevel) : BottomSheetDialogFragment() {
    private lateinit var mBinding: ViewSettingsDialogBinding
    private var mViewModel: SettingsBottomSheetViewModel? = null
    private lateinit var currentEditLevelsKey: String

    override fun onDetach() {
        super.onDetach()
        mViewModel = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ViewSettingsDialogBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewModel()
        setClickListeners()
    }

    private fun configureViewModel() {
        mViewModel = ViewModelProvider(requireActivity())[SettingsBottomSheetViewModel::class.java]
        mViewModel!!.editLevelsKey.observe(viewLifecycleOwner) { key ->
            if (key != null) currentEditLevelsKey = key
        }
        mViewModel!!.getEditLevelsKey()
    }

    private fun setClickListeners() = with (mBinding) {
        createNewLevelButton.setOnClickListener {
            PasswordCheckDialog(currentEditLevelsKey).show(parentFragmentManager, null)
            dismiss()
        }

        addSituationButton.setOnClickListener {
            PasswordCheckDialog(currentEditLevelsKey, gameLevel)
                .show(parentFragmentManager, null)
            dismiss()
        }
    }
}
