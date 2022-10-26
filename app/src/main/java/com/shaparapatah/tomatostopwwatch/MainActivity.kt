package com.shaparapatah.tomatostopwwatch

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shaparapatah.tomatostopwwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val timestampProvider = object : TimestampProvider {
        override fun getMilliseconds(): Long {
            return System.currentTimeMillis()
        }
    }
    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider, ElapsedTimeCalculator(timestampProvider)
            ), ElapsedTimeCalculator(timestampProvider), TimestampMillisecondsFormatter()
        ), CoroutineScope(
            Dispatchers.Main + SupervisorJob()
        )
    )

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val textView = binding.textTime
        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            stopwatchListOrchestrator.ticker.collect {
                textView.text = it
            }
        }
        setupButtons()
    }

    fun setupButtons() {
        binding.buttonStart.setOnClickListener {
            stopwatchListOrchestrator.start()
        }
        binding.buttonPause.setOnClickListener {
            stopwatchListOrchestrator.pause()
        }

        binding.buttonStop.setOnClickListener {
            stopwatchListOrchestrator.stop()
        }
    }
}
