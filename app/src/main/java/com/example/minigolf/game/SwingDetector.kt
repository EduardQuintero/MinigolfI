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
    private val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private var lastForce: Double = 0.0

    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val dy = event.values[1]   // eje Y: arriba/abajo
            val force = Math.abs(dy)

            // Solo golpes fuertes hacia abajo cuentan
            if (force > 20 && dy > 1 ) {
                // dx = 0 porque solo usamos movimiento vertical
                onSwingDetected(force * 1.6, 0f, dy)
            }
        }

        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            val dx = event.values[0]
            val dy = event.values[1]

            // Solo golpes elevados cuentan
            if (lastForce > 15) {
                onSwingDetected(lastForce * 0.9, dx, dy)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    var onSwingDetected: (force: Double, dx: Float, dy: Float) -> Unit = { _, _, _ -> }
}
