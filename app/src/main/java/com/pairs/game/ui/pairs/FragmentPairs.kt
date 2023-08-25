package com.pairs.game.ui.pairs

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.pairs.game.databinding.FragmentPairsBinding
import com.pairs.game.domain.pairs.GameAdapter
import com.pairs.game.ui.other.ViewBindingFragment

class FragmentPairs : ViewBindingFragment<FragmentPairsBinding>(FragmentPairsBinding::inflate) {
    private lateinit var gameAdapter: GameAdapter
    private lateinit var viewModel: GameViewModel
    private val args: FragmentPairsArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()

        viewModel.winCallback = {
            end()
        }

        binding.homeButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.list.observe(viewLifecycleOwner) {
            gameAdapter.items = it.toMutableList()
            gameAdapter.notifyDataSetChanged()
        }
        viewModel.timer.observe(viewLifecycleOwner) { totalSecs ->
            val hours = totalSecs / 3600;
            val minutes = (totalSecs % 3600) / 60;
            val seconds = totalSecs % 60;

            binding.time.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }

        if (viewModel.gameState && !viewModel.pauseState) {
            viewModel.startTimer()
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            GameViewModelFactory(args.difficulty)
        )[GameViewModel::class.java]
    }

    private fun end() {
        viewModel.stopTimer()
        viewModel.gameState = false
        findNavController().navigate(
            FragmentPairsDirections.actionFragmentPairsToDialogWin(
                viewModel.timer.value!!
            )
        )
    }

    private fun initAdapter() {
        gameAdapter = GameAdapter {
            if (viewModel.list.value!!.find { it.closeAnimation } == null && viewModel.list.value!!.find { it.openAnimation } == null) {
                viewModel.openItem(it)
            }
        }
        with(binding.gameRV) {
            adapter = gameAdapter
            layoutManager = GridLayoutManager(requireContext(), 5).also {
                it.orientation = GridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
            itemAnimator = null
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopTimer()
    }
}