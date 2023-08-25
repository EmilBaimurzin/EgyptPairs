package com.pairs.game.ui.start

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.pairs.game.R
import com.pairs.game.databinding.FragmentStartDestinationBinding
import com.pairs.game.ui.other.ViewBindingFragment

class FragmentStartDestination : ViewBindingFragment<FragmentStartDestinationBinding>(FragmentStartDestinationBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exit.setOnClickListener {
            requireActivity().finish()
        }

        binding.play.setOnClickListener {
           findNavController().navigate(R.id.action_fragmentMain_to_fragmentEgyptWheel)
        }

        binding.privacyText.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("https://www.google.com")
                )
            )
        }
    }
}