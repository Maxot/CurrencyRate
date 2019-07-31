package com.maxot.testbank.ui.base


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel(){
//    private val parentJob = Job()
//
//     val coroutineContext: CoroutineContext
//        get() = parentJob + Dispatchers.Default
//
//    val scope = CoroutineScope(coroutineContext)
}