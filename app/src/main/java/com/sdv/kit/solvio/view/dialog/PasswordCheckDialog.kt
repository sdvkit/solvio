package com.sdv.kit.solvio.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sdv.kit.solvio.databinding.ViewPasswordCheckDialogBinding
import com.sdv.kit.solvio.entity.GameLevel
import org.mindrot.jbcrypt.BCrypt

class PasswordCheckDialog(
    private val currentKey: String,
    private val gameLevel: GameLevel?
) : DialogFragment() {
    constructor(currentKey: String) : this(currentKey, null)

    private lateinit var mBinding: ViewPasswordCheckDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ViewPasswordCheckDialogBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() = with (mBinding) {
        checkKeyButton.setOnClickListener {
            val encodedKey = BCrypt.hashpw(keyEditText.text.toString(), BCRYPT_SALT)

            if (currentKey == encodedKey) when (gameLevel == null) {
                true -> {
                    CreateLevelBottomSheetDialog().show(parentFragmentManager, null)
                    dismiss()
                }
                else -> {
                    AddingSituationBottomSheetDialog(gameLevel).show(parentFragmentManager, null)
                    dismiss()
                }
            }
        }
    }

    companion object {
        private const val BCRYPT_SALT = "\$2a\$10\$wyQ6JNPs29DQH6KcZYO.ejtd7S91wCp8T"
    }
}