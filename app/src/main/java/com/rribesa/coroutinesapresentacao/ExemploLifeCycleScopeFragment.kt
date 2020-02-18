package com.rribesa.coroutinesapresentacao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class ExemploLifeCycleScopeFragment : Fragment() {
    private val buttonMain: Button? by lazy { this.view?.findViewById<Button>(R.id.dispatcher_main) }
    private val buttonIO: Button? by lazy { this.view?.findViewById<Button>(R.id.dispatcher_io) }
    private val buttonDefault: Button? by lazy { this.view?.findViewById<Button>(R.id.dispatcher_default) }
    private val buttonUnconfined: Button? by lazy { this.view?.findViewById<Button>(R.id.dispatcher_unconfined) }
    private val buttonExibeLoading: Button? by lazy { this.view?.findViewById<Button>(R.id.exibe_loading) }
    private val loading: ProgressBar? by lazy { this.view?.findViewById<ProgressBar>(R.id.loading) }
    lateinit var viewModel: ExemploViewModel
    private val viewModelFactory = ExemploViewModelFactory()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exemplo_life_cycle_scope, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        Método Criado apenas para verificar a implementacao do LifeCycleScope e olhar o seu javadoc
        */
        viewLifecycleOwner.lifecycleScope.launch {

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        setListeners()
        viewModel.testeAsync()
    }

    private fun setListeners() {
        buttonMain?.setOnClickListener {testeDispatcher(Dispatchers.Main, "MAIN") }
        buttonIO?.setOnClickListener {testeDispatcher(Dispatchers.IO, "IO") }
        buttonDefault?.setOnClickListener {testeDispatcher(Dispatchers.Default, "DEFAULT") }
        buttonUnconfined?.setOnClickListener {testeDispatcher(Dispatchers.Unconfined, "UNCONFINED") }
        buttonExibeLoading?.setOnClickListener { viewModel.exemploCoroutineScope() }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(ExemploViewModel::class.java)
        initObservers()
    }

    private fun initObservers() {
        viewModel.loadingStateView.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.let {
                showLoading(it)
            }
        })
    }

    private fun showLoading(visibilidade: Int) {
        loading?.visibility = visibilidade
    }

    /*
    Método Criado para exemplificar os dispatchers e mostrar a diferente execuão de threads
    de cada Dispatcher
     */
    private fun testeDispatcher(dispatcher: CoroutineDispatcher, tipoLog:String) {
        runBlocking {
            CoroutineScope(dispatcher).launch {
                println(tipoLog+" 1 - Thread ao iniciar coroutine ${Thread.currentThread()} executou.")

                trocaDispatcher(tipoLog)

                println(tipoLog+" 3 - Thread ao finalizar coroutine ${Thread.currentThread()} executou.")
            }
        }
    }

    /*
    Método para trocar o dispatcher no meio da execucao e ver o comportamento final de cada coroutine
     */
    suspend fun trocaDispatcher(tipoLog: String) {
        withContext(Dispatchers.IO) {
            println(tipoLog+" 2 - Thread após executar a suspend ${Thread.currentThread()} executou.")
        }
    }
}
