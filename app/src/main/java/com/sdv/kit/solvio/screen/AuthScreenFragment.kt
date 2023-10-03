package com.sdv.kit.solvio.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.sdv.kit.solvio.contract.ScreenChanger
import com.sdv.kit.solvio.databinding.FragmentScreenAuthBinding
import com.sdv.kit.solvio.util.AuthManagerUtil

class AuthScreenFragment : Fragment() {
    private var mBinding: FragmentScreenAuthBinding? = null
    private var mGoogleActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    private var mScreenChanger: ScreenChanger? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mScreenChanger = context as ScreenChanger
    }

    override fun onDestroy() {
        super.onDestroy()
        mGoogleActivityResultLauncher = null
        mScreenChanger = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentScreenAuthBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerGoogleActivityResultLauncher()
        setClickListeners()
    }

    private fun setClickListeners() {
        mBinding!!.signInGoogleButton.setOnClickListener {
            val signInIntent = AuthManagerUtil(requireContext()).signInIntent
            mGoogleActivityResultLauncher?.launch(signInIntent)
        }
    }

    private fun registerGoogleActivityResultLauncher() {
        mGoogleActivityResultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) try {
                tryGetSignedInAccount(result.data)
                mScreenChanger?.changeScreen(MainMenuScreenFragment())
            } catch (_: ApiException) { }
        }
    }

    private fun tryGetSignedInAccount(data: Intent?) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
    }
}