package com.example.minigolf.game

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class SwingDetector(context: Context) : SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val force = sqrt(
            (event.values[0] * event.values[0] +
                    event.values[1] * event.values[1] +
                    event.values[2] * event.values[2]).toDouble()
        )

        val directionX = event.values[0]
        val directionY = event.values[1]

        onSwingDetected(force, directionX, directionY)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    var onSwingDetected: (force: Double, dx: Float, dy: Float) -> Unit = { _, _, _ -> }
}
