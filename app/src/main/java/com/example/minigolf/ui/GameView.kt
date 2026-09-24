package com.example.minigolf.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.minigolf.game.GameState

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    lateinit var gameState: GameState

    private val paintBall = Paint().apply { color = android.graphics.Color.WHITE }
    private val paintHole = Paint().apply { color = android.graphics.Color.BLACK }
    // Verde lima para el campo
    private val paintField = Paint().apply { color = android.graphics.Color.parseColor("#32CD32") }
    private val paintBorder = Paint().apply {
        color = android.graphics.Color.DKGRAY
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintField)


        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBorder)


        canvas.drawCircle(gameState.holeX, gameState.holeY, 30f, paintHole)


        canvas.drawCircle(gameState.ballX, gameState.ballY, 20f, paintBall)
    }
}
