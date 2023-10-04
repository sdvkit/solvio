package com.sdv.kit.solvio.entity.relation

import com.sdv.kit.solvio.entity.GameLevel
import com.sdv.kit.solvio.entity.Situation

data class GameLevelWithSituations(
    val gameLevel: GameLevel,

    val situations: List<Situation>
)