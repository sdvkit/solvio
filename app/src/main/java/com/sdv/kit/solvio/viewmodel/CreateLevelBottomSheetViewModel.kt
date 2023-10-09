package com.sdv.kit.solvio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sdv.kit.solvio.entity.GameLevel
import com.sdv.kit.solvio.entity.relation.GameLevelWithSituationsAndActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateLevelBottomSheetViewModel(application: Application) : AndroidViewModel(application) {
    fun saveLevel(gameLevel: GameLevel) = CoroutineScope(Dispatchers.IO).launch {
        Firebase.database
            .getReference("levels/${gameLevel.levelName}")
            .setValue(GameLevelWithSituationsAndActions(gameLevel, listOf()))
    }
}