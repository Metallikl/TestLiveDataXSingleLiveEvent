package com.example.testlivedataxsingleliveevent

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.testlivedataxsingleliveevent.databinding.ActivityMainBinding

import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    /**
     * Passo 1
     * Add breakpoint dentro do escopo de viewModel.state.observe(this)
     * Add breakpoint dentro do escopo de viewModel.event.observe(this)
     *
     * Passo 2
     * Clicar no botao Call APi que atualizará o valor de state para loading e depois error e setará
     * o valor de event com uma msg de erro.
     * Tanto os breakpoint de de state quando o de event serão triggados ja que receberam novos valores
     * A tela será configurada para o estado de erro e uma snackbar com a msg de erro será exibida.
     *
     * Rotacionar a tela do device.
     *
     * No momento em que viewModel.state receber um observador, os breakpoints no escopo de state
     * serão trigados, re emitindo o ultimo estado salvo(error).
     * Embora event continue guardando o ultimo valor setado, ele não o re emitirá,
     * pois não teve um valor atribuido.
     *
     * A tela continuara configurada no estado de erro, mas a snackbar nao sera exibida novamente.
     */

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
            //Breakpoint na linha abaixo
            with(binding){
                //Caso decida remover o valor inicial de state, essa safecall evitará um possivel crash
                state?.let {
                    //Breakpoint na linha abaixo
                    textView.text = state.text
                    imageView.isVisible = state.isError
                    progressBar.isVisible = state.isLoading
                    button.isVisible = state.isLoading.not()
                }
            }
        }
        viewModel.event.observe(this){ event->
            //Breakpoint na linha abaixo
            event?.let {
                //Breakpoint na linha abaixo
                Snackbar.make(binding.root,event.message,Snackbar.LENGTH_LONG).show()
            }
        }
    }
}