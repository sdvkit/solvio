package com.sdv.kit.solvio.view.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sdv.kit.solvio.databinding.ViewInfoDialogBinding
import com.sdv.kit.solvio.entity.ReferenceInfo
import com.sdv.kit.solvio.viewmodel.InfoBottomSheetViewModel

class InfoBottomSheetDialog() : BottomSheetDialogFragment() {
    private lateinit var mBinding: ViewInfoDialogBinding
    private var mViewModel: InfoBottomSheetViewModel? = null
    private var mCurrentReferences: List<ReferenceInfo> = listOf()
    private var mCurrentReferenceIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ViewInfoDialogBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewModel()
        setClickListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun configureViews() = with (mBinding) {
        if (mCurrentReferences.isNotEmpty()) {
            pageTextView.text = "${mCurrentReferenceIndex + 1}/${mCurrentReferences.size}"
            infoKeyTextView.text = mCurrentReferences[mCurrentReferenceIndex].key.replace("_", ".")
            valueInfoTextView.text = mCurrentReferences[mCurrentReferenceIndex].value
        }
    }

    private fun configureViewModel() {
        mViewModel = ViewModelProvider(requireActivity())[InfoBottomSheetViewModel::class.java]
        mViewModel!!.referenceInfoList.observe(viewLifecycleOwner) { references ->
            mCurrentReferences = references
            configureViews()
        }
        mViewModel!!.getReferenceInfo()
    }

    private fun setClickListeners() = with (mBinding) {
        prevPageButton.setOnClickListener {
            if (mCurrentReferenceIndex > 0) {
                mCurrentReferenceIndex--
                configureViews()
            }
        }

        nextPageButton.setOnClickListener {
            if (mCurrentReferenceIndex < mCurrentReferences.size - 1) {
                mCurrentReferenceIndex++
                configureViews()
            }
        }
    }
}