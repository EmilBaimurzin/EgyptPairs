package com.pairs.game.domain.pairs

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.pairs.game.R
import com.pairs.game.databinding.ItemPairsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameAdapter(private var onItemClick: ((Int) -> Unit)? = null) : RecyclerView.Adapter<GameViewHolder>() {
    var items = mutableListOf<GameItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            ItemPairsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(items[position])
        holder.onItemClick = onItemClick
    }
}

class GameViewHolder(private val binding: ItemPairsBinding) : RecyclerView.ViewHolder(binding.root) {
    var onItemClick: ((Int) -> Unit)? = null
    fun bind(item: GameItem) {
        binding.apply {
            val img = when (item.value) {
                1 -> R.drawable.symbol01
                2 -> R.drawable.symbol02
                3 -> R.drawable.symbol03
                4 -> R.drawable.symbol04
                else -> R.drawable.symbol05
            }
            val bg = when (item.bgValue) {
                1 -> R.drawable.box01
                2 -> R.drawable.box02
                else -> R.drawable.box03
            }
            if (item.isOpened) {
                imgValue.setImageResource(img)
                binding.root.setBackgroundResource(bg)
            } else {
                imgValue.setImageDrawable(null)
                binding.root.setBackgroundResource(R.drawable.box)
            }
            if (item.openAnimation) {
                flipImage(binding.root, img, bg)
            }

            if (item.closeAnimation) {
                flipImage(binding.root, null, bg)
            }

            binding.root.setOnClickListener {
                if (!item.openAnimation && !item.closeAnimation && !item.isOpened) {
                    onItemClick?.invoke(adapterPosition)
                }
            }
        }
    }

    private fun flipImage(
        view: View,
        @DrawableRes img: Int?,
        @DrawableRes bg: Int,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(200)
            if (img != null) {
                binding.root.setBackgroundResource(bg)
                binding.imgValue.setImageResource(img)
            } else {
                binding.root.setBackgroundResource(R.drawable.box)
                binding.imgValue.setImageDrawable(null)
            }
        }
            val animatorSet = AnimatorSet()
            val rotateAnimator = ObjectAnimator.ofFloat(view, "rotationY", 0f, 180f)
            rotateAnimator.duration = 400

            val scaleXAnimator = ValueAnimator.ofFloat(1f, -1f)
            scaleXAnimator.addUpdateListener { animator ->
                val scale = animator.animatedValue as Float
                view.scaleX = scale
            }
            scaleXAnimator.duration = 400

            animatorSet.playTogether(rotateAnimator, scaleXAnimator)
            animatorSet.start()
        }

}