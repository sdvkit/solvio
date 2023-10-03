package com.sdv.kit.solvio.entity

data class GameLevel(
    val levelName: String,
    val backgroundImageSrc: String,
    val levelDescription: String,
    val situationsCount: Int
) {
    class Builder {
        private var levelName: String = ""
        private var backgroundImageSrc: String = ""
        private var levelDescription: String = ""
        private var situationsCount: Int = 0

        fun levelName(levelName: String) = apply { this.levelName = levelName }
        fun backgroundImageUrl(backgroundImageSrc: String) = apply { this.backgroundImageSrc = backgroundImageSrc }
        fun levelDescription(levelDescription: String) = apply { this.levelDescription = levelDescription }
        fun situationsCount(situationsCount: Int) = apply { this.situationsCount = situationsCount }
        fun build(): GameLevel = GameLevel(levelName, backgroundImageSrc, levelDescription, situationsCount)
    }
}