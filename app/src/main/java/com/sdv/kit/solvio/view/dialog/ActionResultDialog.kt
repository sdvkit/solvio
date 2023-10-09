package com.sdv.kit.solvio.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.sdv.kit.solvio.R
import com.sdv.kit.solvio.databinding.ViewActionResultDialogBinding
import com.sdv.kit.solvio.entity.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActionResultDialog : DialogFragment() {
    private lateinit var mBinding: ViewActionResultDialogBinding

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
        return dialog
    }

    private fun configureViews() = with (mBinding) {
        val action = Gson().fromJson(requireArguments().getString(ACTION_KEY), Action::class.java)
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

    companion object {
        private const val ACTION_KEY = "action"

        fun instance(action: Action): ActionResultDialog {
            val actionResultDialog = ActionResultDialog()

            val args = Bundle().apply {
                putString(ACTION_KEY, Gson().toJson(action))
            }

            actionResultDialog.arguments = args
            return actionResultDialog
        }
    }
}