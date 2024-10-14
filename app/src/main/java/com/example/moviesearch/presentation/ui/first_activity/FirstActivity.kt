package com.example.moviesearch.presentation.ui.first_activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.databinding.ActivityFirstBinding
import com.example.moviesearch.presentation.ui.FilterYear.FilterYearViewModel
import com.example.moviesearch.presentation.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FirstActivity : AppCompatActivity() {
    private var delayMils: Long = 100
    private lateinit var binding: ActivityFirstBinding
    private val viewModel: FirstActivityViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (application as App).db.movieDao()
                return FirstActivityViewModel(wordsDao) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        runBlocking {
            viewModel.loadFirstDataBase(1, "First")
            val job = launch {
                viewModel.getIndex().collect {
                    if (it[0].firstUse == "Second") {
                        delayMils = 100
                    } else {
                        delayMils = 7000
                        viewModel.upDateFirstDataBase(1, "Second")
                    }
                }
            }
            delay(100)
            job.cancel()
        }
        binding.imageButtonSplashFirst.setOnClickListener {
            binding.imageButtonSplashFirst.isVisible = false
            binding.imageButtonSecond.isVisible = true
        }
        binding.imageButtonSecond.setOnClickListener {
            binding.imageButtonSecond.isVisible = false
            binding.imageButtonThird.isVisible = true
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMils)
        setContentView(binding.root)


    }
}