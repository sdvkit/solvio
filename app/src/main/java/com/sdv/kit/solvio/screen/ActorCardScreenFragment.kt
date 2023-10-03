package com.sdv.kit.solvio.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdv.kit.solvio.databinding.FragmentScreenActorCardBinding

class ActorCardScreenFragment : Fragment() {
    private var mBinding: FragmentScreenActorCardBinding? = null

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentScreenActorCardBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }
}