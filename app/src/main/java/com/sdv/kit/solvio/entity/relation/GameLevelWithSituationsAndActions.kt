package com.sdv.kit.solvio.entity.relation

import com.sdv.kit.solvio.entity.GameLevel

data class GameLevelWithSituationsAndActions(
    val gameLevel: GameLevel,

    val situations: List<SituationWithActions>
)