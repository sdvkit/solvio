package com.sdv.kit.solvio.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sdv.kit.solvio.databinding.ViewPasswordCheckDialogBinding
import com.sdv.kit.solvio.entity.EditOption
import org.mindrot.jbcrypt.BCrypt

class PasswordCheckDialog(
    private val currentKey: String,
    private val editOption: EditOption
) : DialogFragment() {
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

            if (currentKey == encodedKey) when (editOption) {
                EditOption.CREATE_LEVEL -> {
                    CreateLevelBottomSheetDialog().show(childFragmentManager, null)
                }
                EditOption.ADD_SITUATION -> {}
            }
        }
    }

    companion object {
        private const val BCRYPT_SALT = "\$2a\$10\$wyQ6JNPs29DQH6KcZYO.ejtd7S91wCp8T"
    }
}