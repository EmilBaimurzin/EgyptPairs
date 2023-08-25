package com.pairs.game.ui.wheel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pairs.game.core.library.random

class EgyptWheelViewModel: ViewModel() {
    var rotation4 = (1 random 360).toFloat()
    var rotation3 = (1 random 360).toFloat()
    var rotation2 = (1 random 360).toFloat()
    private val _solved = MutableLiveData(false)
    val solved: LiveData<Boolean> = _solved
    fun areCirclesSolved() {
        val normalizedAngle1 = rotation4 % 360
        val normalizedAngle2 = rotation3 % 360
        val normalizedAngle3 = rotation2 % 360

        val angleDifference1 = Math.abs(normalizedAngle2 - normalizedAngle1)
        val angleDifference2 = Math.abs(normalizedAngle3 - normalizedAngle2)

        _solved.postValue(angleDifference1 <= 10 && angleDifference2 <= 10)
    }
}