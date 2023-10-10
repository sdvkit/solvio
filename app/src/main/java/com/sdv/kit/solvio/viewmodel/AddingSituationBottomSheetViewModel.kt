package com.sdv.kit.solvio.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sdv.kit.solvio.entity.GameLevel
import com.sdv.kit.solvio.entity.Indexes
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituationsAndActions
import com.sdv.kit.solvio.entity.relation.SituationWithActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddingSituationBottomSheetViewModel(application: Application) : AndroidViewModel(application) {
    private val _indexes = MutableLiveData<Indexes>()
    val indexes: LiveData<Indexes> = _indexes

    fun getFirebaseIndexes() = CoroutineScope(Dispatchers.IO).launch {
        Firebase.database.getReference("indexes/").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val receivedIndexes = snapshot.value as Map<*, *>
                    _indexes.value = Indexes(
                        receivedIndexes["nextActionId"] as Long,
                        receivedIndexes["nextSituationId"] as Long
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }

    fun saveSituationWithActions(data: GameLevelWithSituationsAndActions) = CoroutineScope(Dispatchers.IO).launch {
        val ref = Firebase.database.getReference("levels/${data.gameLevel.levelName}/situations/")
        val situation = data.situations[0].situation
        val actions = data.situations[0].actions

        ref.child("${situation.situationId}/situationId").setValue(situation.situationId)
        ref.child("${situation.situationId}/situationDescription").setValue(situation.situationDescription)
        ref.child("${situation.situationId}/actorName").setValue(situation.actorName)
        ref.child("${situation.situationId}/actorImageUrl").setValue(situation.actorImageUrl)

        actions.forEach { action ->
            ref.child("${situation.situationId}/actions/${action.actionId}/actionDescription").setValue(action.actionDescription)
            ref.child("${situation.situationId}/actions/${action.actionId}/actionId").setValue(action.actionId)
            ref.child("${situation.situationId}/actions/${action.actionId}/actionResult").setValue(action.actionResult)
            ref.child("${situation.situationId}/actions/${action.actionId}/actionResultImageUrl").setValue(action.actionResultImageUrl)
            ref.child("${situation.situationId}/actions/${action.actionId}/isPositive").setValue(action.isPositive)
        }

        incrementIndexes()
    }

    private suspend fun incrementIndexes() = withContext(Dispatchers.IO) {
        val ref = Firebase.database.getReference("indexes/")
        ref.child("nextSituationId").setValue(_indexes.value!!.nextActionId + 1)
        ref.child("nextActionId").setValue(_indexes.value!!.nextActionId + 2)
    }
}