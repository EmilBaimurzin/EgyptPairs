package com.pairs.game.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pairs.game.R
import com.pairs.game.core.library.ViewBindingDialog
import com.pairs.game.databinding.DialogWinBinding
import com.pairs.game.domain.pairs.Difficulty
import com.pairs.game.ui.wheel.FragmentEgyptWheelDirections

class DialogWin : ViewBindingDialog<DialogWinBinding>(DialogWinBinding::inflate) {
    private val args: DialogWinArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog_No_Border)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCancelable(false)
        dialog!!.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                findNavController().popBackStack(R.id.fragmentMain, false, false)
                true
            } else {
                false
            }
        }

        val hours = args.time / 3600;
        val minutes = (args.time % 3600) / 60;
        val seconds = args.time % 60;

        binding.time.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        binding.easy.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMain, false, false)
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentEgyptWheel)
            findNavController().navigate(
                FragmentEgyptWheelDirections.actionFragmentEgyptWheelToFragmentPairs(
                    Difficulty.EASY
                )
            )
        }
        binding.normal.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMain, false, false)
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentEgyptWheel)
            findNavController().navigate(
                FragmentEgyptWheelDirections.actionFragmentEgyptWheelToFragmentPairs(
                    Difficulty.NORMAL
                )
            )
        }
        binding.hard.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMain, false, false)
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentEgyptWheel)
            findNavController().navigate(
                FragmentEgyptWheelDirections.actionFragmentEgyptWheelToFragmentPairs(
                    Difficulty.HARD
                )
            )
        }
        binding.close.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMain, false, false)
        }
    }
}