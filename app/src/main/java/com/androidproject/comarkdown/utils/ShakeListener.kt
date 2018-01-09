package com.androidproject.comarkdown.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import kotlin.math.sqrt

/**
 * Created by evan on 2018/1/9.
 */
class ShakeListener(context: Context) : SensorEventListener {
    private val UPTATE_INTERVAL_TIME = 100
    private val SPEED_THRESHOLD = 2000
    private val viewContext: Context
    private val sensorManager: SensorManager

    private var lastUpdateTime: Long = 0
    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var lastZ: Float = 0f

    init {
        viewContext = context
        sensorManager = viewContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val currentUpdateTime = System.currentTimeMillis()
        val timeInterval = currentUpdateTime - lastUpdateTime
        if (timeInterval < UPTATE_INTERVAL_TIME) {
            return
        }

        lastUpdateTime = currentUpdateTime
        val values = p0?.values
        if (values != null) {
            val x = values[0]
            val y = values[1]
            val z = values[2]

            val deltaX = x - lastX
            val deltaY = y - lastY
            val deltaZ = z - lastZ

            lastX = x
            lastY = y
            lastZ = z

            val speed = sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / timeInterval * 10000
            if (speed > SPEED_THRESHOLD) {
                Toast.makeText(viewContext, "shake it off", Toast.LENGTH_SHORT).show()
            }
        } else {
            return
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    fun registerSensor() {
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterSensor() {
        lastX = 0f
        lastY = 0f
        lastZ = 0f
        sensorManager.unregisterListener(this)
    }
}