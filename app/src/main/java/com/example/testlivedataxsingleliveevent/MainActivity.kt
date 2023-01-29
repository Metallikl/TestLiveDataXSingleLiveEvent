package com.example.testlivedataxsingleliveevent

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import br.com.luche.FoodLogin.MainViewModel
import com.example.testlivedataxsingleliveevent.databinding.ActivityMainBinding

import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()
        initActions()
    }

    private fun initActions() {
        binding.button.setOnClickListener {
            viewModel.callApi()
        }

        binding.btnReset.setOnClickListener {
            viewModel.resetState()
        }
    }

    private fun setObserver() {
        viewModel.state.observe(this) { state ->
            with(binding){
                textView.text = state.text
                imageView.isVisible = state.isError
                progressBar.isVisible = state.isLoading
                button.isVisible = state.isLoading.not()
            }
        }
        viewModel.event.observe(this){ event->
            event?.let {
                Snackbar.make(binding.root,event.message,Snackbar.LENGTH_LONG).show()
            }
        }
    }

}