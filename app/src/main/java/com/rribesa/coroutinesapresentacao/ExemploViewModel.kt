package com.rribesa.coroutinesapresentacao

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.util.*

class ExemploViewModel : ViewModel() {
    private val supervisorJob = SupervisorJob()
    private val job = Job()

    private val loadingState: MutableLiveData<Int> = MutableLiveData()
    val loadingStateView: LiveData<Int> = loadingState

    /*
    Método Criado apenas para verificar a implementacao do viewModelScope e olhar o seu javadoc
    */
    fun testeViewModelScope() {
        viewModelScope.launch {

        }
    }

    /*
    Método Criado apenas para verificar a implementacao do GlobalScope e olhar o seu javadoc
    */
    fun exemploGlobalScope() {
        GlobalScope.launch {

        }
    }

    /*
    Método Criado para exemplificar o uso de um CoroutineScope Junto da ViewModel
     */
    fun exemploCoroutineScope() {
        CoroutineScope(Dispatchers.Main + supervisorJob).launch {
            loadingState.postValue(View.VISIBLE)
        }
    }

    /*
    Método Criado apenas para verificar a implementacao do MainScope e olhar o seu javadoc
     */
    fun exemploMainScope() {
        MainScope().launch {
        }
    }

    /*
    Método criado para mostrar o que é um deferred e como efetuar uma execucao do tipo async
     */
    fun testeAsync() {
        viewModelScope.launch {
            val testeA = async { teste() }
            val testeB = async { teste2() }
            println(testeA.await() + testeB.await())
        }
    }

    private suspend fun teste(): String =
        withContext(Dispatchers.Default) {
            delay(5000)
            "teste de execucao 1 terminou  em:" + Date() + "\n\n"
        }


    private suspend fun teste2(): String =
        withContext(Dispatchers.Default) {
            "teste de execucao 2 terminou  em:" + Date()
        }


    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancelChildren()
    }
}