package com.sdv.kit.solvio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sdv.kit.solvio.entity.GameLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateLevelBottomSheetViewModel(application: Application) : AndroidViewModel(application) {
    fun saveLevel(gameLevel: GameLevel) = CoroutineScope(Dispatchers.IO).launch {
        val ref = Firebase.database.getReference("levels/${gameLevel.levelName}")
        ref.child("levelName").setValue(gameLevel.levelName)
        ref.child("levelDescription").setValue(gameLevel.levelDescription)
        ref.child("levelDescription").setValue(gameLevel.levelDescription)
        ref.child("backgroundImageUrl").setValue(gameLevel.backgroundImageSrc)
        ref.child("situations").setValue(listOf<String>())
    }
}