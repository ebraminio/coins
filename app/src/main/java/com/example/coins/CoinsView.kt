package com.example.coins

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.Toast

// Interview related project
// This work has been done between 23:20 and 00:25
// Optimized for efficiency, battery and memory, well,
// as far as possible in usual Android programmings :)
class CoinsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var positionOfCoins = listOf<Pair<Float, Float>>()
    var progress = 0f

    init {
        setOnClickListener {
            positionOfCoins = (1..Math.ceil(Math.random() * 50F).toInt()).map {
                Pair((Math.random() * 100f).toFloat(), (Math.random() * 100f).toFloat())
            }

            ValueAnimator.ofFloat(0f, 100f).apply {
                duration = 400L
                interpolator = AccelerateInterpolator()
                addUpdateListener { value ->
                    progress = value.animatedValue as Float
                    invalidate()
                }
            }.start()
        }
    }

    val p1 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
    }
    val p2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFD700")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val cx = width / 2f
        val cy = height / 2f
        val buttonRadius = minOf(height, width) / 6f
        canvas.drawCircle(cx, cy, buttonRadius, p1)

        if (progress == 0f) return

        p2.alpha = (100 - progress.toInt()) * 256 / 100
        positionOfCoins.forEach {
            canvas.drawCircle(
                cx - cx * progress / 80 - (it.first * buttonRadius / 80 - buttonRadius / 2),
                cy - (it.second * buttonRadius / 80 - buttonRadius / 2),
                buttonRadius / 10, p2)
        }
    }
}