package com.sdv.kit.solvio.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdv.kit.solvio.contract.ScreenChanger
import com.sdv.kit.solvio.databinding.FragmentScreenAuthBinding

class AuthScreenFragment : Fragment() {
    private var mBinding: FragmentScreenAuthBinding? = null
    private var mScreenChanger: ScreenChanger? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mScreenChanger = context as ScreenChanger
    }

    override fun onDestroy() {
        super.onDestroy()
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
        setClickListeners()
    }

    private fun setClickListeners() {
        mBinding!!.signInGoogleButton.setOnClickListener {
            mScreenChanger?.changeScreen(MainMenuScreenFragment())
        }
    }
}