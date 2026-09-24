package com.example.minigolf.game

import kotlin.math.pow
import kotlin.math.sqrt

data class GameState(
    var holeNumber: Int = 1,
    var par: Int = 3,
    var strokes: Int = 0,
    var ballX: Float = 0f,
    var ballY: Float = 0f,
    var holeX: Float = 0f,
    var holeY: Float = 0f
) {
    fun initPositions(maxWidth: Float, maxHeight: Float, orientation: String) {
        if (orientation == "vertical") {
            // Hoyo arriba centrado
            holeX = maxWidth / 2
            holeY = maxHeight * 0.1f
            // Pelota abajo centrada
            ballX = maxWidth / 2
            ballY = maxHeight * 0.9f
        } else {
            // Hoyo a la izquierda centrado
            holeX = maxWidth * 0.1f
            holeY = maxHeight / 2
            // Pelota a la derecha centrada
            ballX = maxWidth * 0.9f
            ballY = maxHeight / 2
        }
    }

    fun resetHole(maxWidth: Float, maxHeight: Float, orientation: String) {
        strokes = 0
        initPositions(maxWidth, maxHeight, orientation)
    }

    fun applySwing(force: Double, dx: Float, dy: Float, maxWidth: Float, maxHeight: Float) {
        strokes++
        ballX += (dx * force).toFloat()
        ballY += (dy * force).toFloat()

        // Limitar dentro del campo
        if (ballX < 20f) ballX = 20f
        if (ballY < 20f) ballY = 20f
        if (ballX > maxWidth - 20f) ballX = maxWidth - 20f
        if (ballY > maxHeight - 20f) ballY = maxHeight - 20f
    }

    fun isBallInHole(): Boolean {
        val distance = sqrt((ballX - holeX).toDouble().pow(2) + (ballY - holeY).toDouble().pow(2))
        return distance < 40
    }
}
