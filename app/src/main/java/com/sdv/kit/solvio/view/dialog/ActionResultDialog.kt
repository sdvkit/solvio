package com.sdv.kit.solvio.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sdv.kit.solvio.R
import com.sdv.kit.solvio.databinding.ViewActionResultDialogBinding
import com.sdv.kit.solvio.entity.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActionResultDialog(
    private val action: Action,
    private val onFinishAction: () -> Unit
) : DialogFragment() {
    constructor(action: Action) : this(action, {})

    private lateinit var mBinding: ViewActionResultDialogBinding

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onFinishAction()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ViewActionResultDialogBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation

        dialog.setOnShowListener {
            dialog.window?.decorView?.bringToFront()
        }

        return dialog
    }

    private fun configureViews() = with (mBinding) {
        loadActionImage(actionResultImageView, action.actionResultImageUrl)
        setClickListeners()

        actionResultTextView.text = action.actionResult
        actionResultBackground.setBackgroundColor(getBackgroundColor(action))
    }

    private fun setClickListeners() = with (mBinding) {
        closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun getBackgroundColor(action: Action): Int = when (action.isPositive) {
        true -> ContextCompat.getColor(requireContext(), R.color.green)
        else -> ContextCompat.getColor(requireContext(), R.color.red)
    }

    private fun loadActionImage(
        actionResultImageView: ImageView,
        actionResultImageUrl: String
    ) = CoroutineScope(Dispatchers.Main).launch {
        Glide.with(requireContext())
            .load(actionResultImageUrl)
            .placeholder(R.drawable.image_logo)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(actionResultImageView)
    }
}