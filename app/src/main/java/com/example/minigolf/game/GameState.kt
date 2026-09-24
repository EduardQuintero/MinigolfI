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
    fun initPositions(maxWidth: Float, maxHeight: Float, orientation: String, topMargin: Float) {
        if (orientation == "vertical") {
            // Hoyo arriba centrado, pero debajo del menú
            holeX = maxWidth / 2
            holeY = topMargin + 150f
            // Pelota abajo centrada
            ballX = maxWidth / 2
            ballY = maxHeight - 100f
        } else {
            // Hoyo a la izquierda centrado
            holeX = 120f
            holeY = (maxHeight / 2).coerceAtLeast(topMargin + 150f)
            // Pelota a la derecha centrada
            ballX = maxWidth - 120f
            ballY = maxHeight / 2
        }
    }

    fun resetHole(maxWidth: Float, maxHeight: Float, orientation: String, topMargin: Float) {
        strokes = 0
        initPositions(maxWidth, maxHeight, orientation, topMargin)
    }

    fun applySwing(force: Double, dx: Float, dy: Float, maxWidth: Float, maxHeight: Float, orientation: String, topMargin: Float) {
        strokes++

        if (orientation == "vertical") {
            // Movimiento hacia arriba con leve variación lateral
            ballY -= (force * 8).toFloat()
            ballX += (dx * 0.2f)
        } else {
            // Movimiento hacia la izquierda con leve variación vertical
            ballX -= (force * 8).toFloat()
            ballY += (dy * 0.2f)
        }

        // Limitar dentro del campo (debajo del menú)
        if (ballX < 20f) ballX = 20f
        if (ballY < topMargin + 60f) ballY = topMargin + 60f
        if (ballX > maxWidth - 20f) ballX = maxWidth - 20f
        if (ballY > maxHeight - 20f) ballY = maxHeight - 20f
    }

    fun isBallInHole(): Boolean {
        val distance = sqrt((ballX - holeX).toDouble().pow(2) + (ballY - holeY).toDouble().pow(2))

        val holeRadius = 32.6
        val proximityFactor = 0.98 // proximidad

        // Si la pelota entra dentro del radio ajustado, se considera completado
        return distance <= holeRadius * proximityFactor
    }

}
