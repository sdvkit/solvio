package com.sdv.kit.solvio.entity

data class Choice(
    val situation: Situation,
    val actionIndex: Int,
    val isActionPositive: Boolean
)