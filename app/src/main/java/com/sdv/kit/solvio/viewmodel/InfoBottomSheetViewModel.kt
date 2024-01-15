package com.sdv.kit.solvio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sdv.kit.solvio.entity.ReferenceInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoBottomSheetViewModel(application: Application) : AndroidViewModel(application) {
    private val _referenceInfoList = MutableLiveData(listOf<ReferenceInfo>())
    val referenceInfoList: LiveData<List<ReferenceInfo>> = _referenceInfoList

    fun getReferenceInfo() = CoroutineScope(Dispatchers.IO).launch {
        Firebase.database.getReference("reference_info/").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val references = mutableListOf<ReferenceInfo>()

                    (snapshot.value as HashMap<*,*>).forEach {
                        val refInfo = ReferenceInfo(it.key.toString(), it.value.toString())
                        references.add(refInfo)
                    }

                    _referenceInfoList.value = references.sortedBy { it.key }
                }
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }
}