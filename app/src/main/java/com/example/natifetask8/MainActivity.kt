package com.example.natifetask8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.natifetask8.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val generator: NumberGenerator by lazy {
        NumberGenerator()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        startGenerateNumber()
        observeGenerator()
    }

    private fun observeGenerator() {
        generator.liveDataValue.observe(this) {
            binding?.number?.text = it.toString()
        }
//        lifecycleScope.launchWhenStarted {
//            generator.flowValue.collectLatest {
//                binding?.number?.text = it.toString()
//            }
//        }
    }

    private fun startGenerateNumber() {
        binding?.startButton?.setOnClickListener {
            generator.liveDataCase()
//            generator.coroutineCase()
//            generator.rxCase()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    binding?.number?.text = it.toString()
//                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}