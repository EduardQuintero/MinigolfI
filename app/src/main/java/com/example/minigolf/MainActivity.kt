package com.example.minigolf

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.view.WindowInsets
import android.view.WindowInsetsController
import com.google.androidgamesdk.GameActivity
import com.example.minigolf.game.GameState
import com.example.minigolf.game.SwingDetector
import com.example.minigolf.ui.GameView
import com.example.minigolf.ui.InstructionsDialog

class MainActivity : GameActivity() {

    private lateinit var swingDetector: SwingDetector
    private lateinit var gameState: GameState
    private lateinit var gameView: GameView
    private lateinit var strokesText: TextView
    private lateinit var resetButton: Button

    companion object {
        init {
            System.loadLibrary("minigolf")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameState = GameState()
        setContentView(R.layout.activity_main)

        gameView = findViewById(R.id.gameView)
        gameView.gameState = gameState

        strokesText = findViewById(R.id.strokesText)
        resetButton = findViewById(R.id.resetButton)

        // Mostrar instrucciones al inicio
        InstructionsDialog.show(this)

        swingDetector = SwingDetector(this)
        swingDetector.onSwingDetected = { force, dx, dy ->
            val orientation = if (resources.configuration.orientation ==
                android.content.res.Configuration.ORIENTATION_PORTRAIT) "vertical" else "horizontal"
            val topMargin = 200f // margen superior dinámico debajo del menú

            gameState.applySwing(
                force,
                dx,
                dy,
                gameView.width.toFloat(),
                gameView.height.toFloat(),
                orientation,
                topMargin
            )
            updateUI()
            if (gameState.isBallInHole()) {
                showStatsMenu()
            }
            gameView.invalidate()
        }

        resetButton.setOnClickListener {
            val orientation = if (resources.configuration.orientation ==
                android.content.res.Configuration.ORIENTATION_PORTRAIT) "vertical" else "horizontal"
            val topMargin = 200f
            gameState.resetHole(gameView.width.toFloat(), gameView.height.toFloat(), orientation, topMargin)
            updateUI()
            gameView.invalidate()
        }
    }

    private fun updateUI() {
        strokesText.text = "${getString(R.string.strokes_label)}: ${gameState.strokes} | " +
                "${getString(R.string.hole_label)}: ${gameState.holeNumber} (${getString(R.string.par_label)} ${gameState.par})"
    }

    override fun onResume() {
        super.onResume()
        swingDetector.start()
        val orientation = if (resources.configuration.orientation ==
            android.content.res.Configuration.ORIENTATION_PORTRAIT) "vertical" else "horizontal"
        val topMargin = 200f
        gameState.initPositions(gameView.width.toFloat(), gameView.height.toFloat(), orientation, topMargin)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUi()
        }
    }

    private fun hideSystemUi() {
        window.insetsController?.apply {
            hide(WindowInsets.Type.systemBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showStatsMenu() {
        val message = "¡Hoyo completado!\n" +
                "Golpes: ${gameState.strokes}\n" +
                "Par: ${gameState.par}\n" +
                "Hoyo: ${gameState.holeNumber}"

        android.app.AlertDialog.Builder(this)
            .setTitle("Estadísticas")
            .setMessage(message)
            .setPositiveButton("Continuar") { _, _ ->
                val orientation = if (resources.configuration.orientation ==
                    android.content.res.Configuration.ORIENTATION_PORTRAIT) "vertical" else "horizontal"
                val topMargin = 200f
                gameState.resetHole(gameView.width.toFloat(), gameView.height.toFloat(), orientation, topMargin)
                updateUI()
                gameView.invalidate()
            }
            .show()
    }
}
