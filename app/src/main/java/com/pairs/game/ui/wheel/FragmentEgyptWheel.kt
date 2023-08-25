package com.pairs.game.ui.wheel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pairs.game.databinding.FragmentEgyptWheelBinding
import com.pairs.game.domain.pairs.Difficulty
import com.pairs.game.ui.other.ViewBindingFragment
import java.lang.Math.atan2
import java.lang.Math.sqrt
import kotlin.math.pow


class FragmentEgyptWheel :
    ViewBindingFragment<FragmentEgyptWheelBinding>(FragmentEgyptWheelBinding::inflate) {
    private var rotationAngle4 = 0f
    private var previousAngle4 = 0f
    private var centerX4 = 0.0
    private var centerY4 = 0.0

    private var rotationAngle3 = 0f
    private var previousAngle3 = 0f
    private var centerX3 = 0.0
    private var centerY3 = 0.0

    private var rotationAngle2 = 0f
    private var previousAngle2 = 0f
    private var centerX2 = 0.0
    private var centerY2 = 0.0

    private val viewModel: EgyptWheelViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.home.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.easy.setOnClickListener {
            findNavController().navigate(FragmentEgyptWheelDirections.actionFragmentEgyptWheelToFragmentPairs(Difficulty.EASY))
        }
        binding.normal.setOnClickListener {
            findNavController().navigate(FragmentEgyptWheelDirections.actionFragmentEgyptWheelToFragmentPairs(Difficulty.NORMAL))
        }
        binding.hard.setOnClickListener {
            findNavController().navigate(FragmentEgyptWheelDirections.actionFragmentEgyptWheelToFragmentPairs(Difficulty.HARD))
        }
        previousAngle4 = viewModel.rotation4
        previousAngle3 = viewModel.rotation3
        previousAngle2 = viewModel.rotation2

        binding.wheel4.post {
            centerX4 = binding.root.width / 2.0
            centerY4 = (binding.wheelOutside.y + (binding.wheelOutside.height / 2)).toDouble()
        }

        binding.wheel3.post {
            centerX3 = binding.root.width / 2.0
            centerY3 = (binding.wheelOutside.y + (binding.wheelOutside.height / 2)).toDouble()
        }

        binding.wheel2.post {
            centerX2 = binding.root.width / 2.0
            centerY2 = (binding.wheelOutside.y + (binding.wheelOutside.height / 2)).toDouble()
        }

        viewModel.solved.observe(viewLifecycleOwner) {
            binding.apply {
                easy.isEnabled = it
                normal.isEnabled = it
                hard.isEnabled = it
            }
        }

        binding.wheel4.rotation = viewModel.rotation4
        binding.wheel3.rotation = viewModel.rotation3
        binding.wheel2.rotation = viewModel.rotation2


        binding.wheel4.setOnTouchListener { view, event ->
            val x = event.rawX.toDouble()
            val y = event.rawY.toDouble()

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    previousAngle4 = calculateAngle(x, y, centerX4, centerY4)
                }

                MotionEvent.ACTION_MOVE -> {
                    val minDistance = 5f
                    val distanceMoved = sqrt((x - centerX4).pow(2) + (y - centerY4).pow(2))
                    if (distanceMoved >= minDistance) {
                        val angle = calculateAngle(x, y, centerX4, centerY4)
                        val deltaAngle = angle - previousAngle4
                        rotationAngle4 += deltaAngle
                        view.rotation = rotationAngle4
                        viewModel.rotation4 = rotationAngle4
                        viewModel.areCirclesSolved()
                        previousAngle4 = angle
                    }
                }
            }
            true
        }

        binding.wheel3.setOnTouchListener { view, event ->
            val x = event.rawX.toDouble()
            val y = event.rawY.toDouble()

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    previousAngle3 = calculateAngle(x, y, centerX3, centerY3)
                }

                MotionEvent.ACTION_MOVE -> {
                    val minDistance = 5f
                    val distanceMoved = sqrt((x - centerX3).pow(2) + (y - centerY3).pow(2))
                    if (distanceMoved >= minDistance) {
                        val angle = calculateAngle(x, y, centerX3, centerY3)
                        val deltaAngle = angle - previousAngle3
                        rotationAngle3 += deltaAngle
                        view.rotation = rotationAngle3
                        viewModel.rotation3 = rotationAngle3
                        viewModel.areCirclesSolved()
                        previousAngle3 = angle
                    }
                }
            }
            true
        }

        binding.wheel2.setOnTouchListener { view, event ->
            val x = event.rawX.toDouble()
            val y = event.rawY.toDouble()

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    previousAngle2 = calculateAngle(x, y, centerX2, centerY2)
                }

                MotionEvent.ACTION_MOVE -> {
                    val minDistance = 5f
                    val distanceMoved = sqrt((x - centerX2).pow(2) + (y - centerY2).pow(2))
                    if (distanceMoved >= minDistance) {
                        val angle = calculateAngle(x, y, centerX2, centerY2)
                        val deltaAngle = angle - previousAngle2
                        rotationAngle2 += deltaAngle
                        view.rotation = rotationAngle2
                        viewModel.rotation2 = rotationAngle2
                        viewModel.areCirclesSolved()
                        previousAngle2 = angle
                    }
                }
            }
            true
        }
    }

    private fun calculateAngle(x: Double, y: Double, centerY: Double, centerX: Double): Float {
        return Math.toDegrees(atan2(y - centerY, x - centerX)).toFloat()
    }
}


