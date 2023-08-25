package com.pairs.game.domain.pairs

data class GameItem(
    val value: Int,
    val bgValue: Int,
    var isOpened: Boolean = false,
    var openAnimation: Boolean = false,
    var closeAnimation: Boolean = false
)