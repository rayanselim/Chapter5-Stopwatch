package com.hfad.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.hfad.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var running = false //Is the stopwatch running?
    var offset: Long = 0 //The base offset for the stopwatch

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)
            if (savedInstanceState != null) {
                offset = savedInstanceState.getLong(OFFSET_KEY)
                running = savedInstanceState.getBoolean(RUNNING_KEY)
                if (running) {
                    binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                    binding.stopwatch.start()
                } else setBaseTime()
            }
            binding.startButton.setOnClickListener{

                if (!running) {
                    setBaseTime()
                    binding.stopwatch.start()
                    running = true
                }
            }

            binding.pauseButton.setOnClickListener {
                if (running) {
                    saveOffset()
                    binding.stopwatch.stop()
                    running = false
                }
            }
            binding.resetButton.setOnClickListener {
                binding.stopwatch.stop()
                running = false
                offset = 0
                setBaseTime()

            }
        }
    override fun onPause() {
        super.onPause()
        if (running) {
            saveOffset()
            binding.stopwatch.stop()
        }
    }
    override fun onResume() {
        super.onResume()
        if (running) {
            setBaseTime()
            binding.stopwatch.start()
            offset = 0
        }
    }

        override fun onSaveInstanceState(savedInstanceState: Bundle) {
            savedInstanceState.putLong(OFFSET_KEY, offset)
            savedInstanceState.putBoolean(RUNNING_KEY, running)
            savedInstanceState.putLong(BASE_KEY, binding.stopwatch.base)
            super.onSaveInstanceState(savedInstanceState)
        }

        fun setBaseTime() {
            binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
        }
        fun saveOffset() {
            offset = SystemClock.elapsedRealtime() - binding.stopwatch.base
        }
    }