package com.example.minigolf.game

import kotlin.math.pow
import kotlin.math.sqrt

data class GameState(
    var holeNumber: Int = 1,
    var par: Int = 3,
    var strokes: Int = 0,
    var ballX: Float = 200f,
    var ballY: Float = 600f,
    val holeX: Float = 600f,
    val holeY: Float = 600f
) {
    fun resetHole() {
        strokes = 0
        ballX = 200f
        ballY = 600f
    }

    fun applySwing(force: Double, dx: Float, dy: Float) {
        strokes++
        ballX += (dx * force).toFloat()
        ballY += (dy * force).toFloat()
    }

    fun isBallInHole(): Boolean {
        val distance = sqrt((ballX - holeX).toDouble().pow(2) + (ballY - holeY).toDouble().pow(2))
        return distance < 40 // margen de tolerancia
    }
}
