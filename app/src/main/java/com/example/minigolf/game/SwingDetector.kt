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
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val dx = event.values[0]
            val dy = event.values[1]
            val force = sqrt((dx * dx + dy * dy).toDouble())

            // Solo golpes elevados cuentan
            if (force > 15) {
                onSwingDetected(force * 0.4, dx, dy)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    var onSwingDetected: (force: Double, dx: Float, dy: Float) -> Unit = { _, _, _ -> }
}
