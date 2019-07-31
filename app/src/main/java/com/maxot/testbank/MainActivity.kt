package com.maxot.testbank

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.maxot.BR
import com.maxot.R
import com.maxot.databinding.ActivityMainBinding
import com.maxot.testbank.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        //Binding ViewModel
        mViewDataBinding.viewModel = getViewModel()

        navController = Navigation.findNavController(this, R.id.navFragment)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main

    }

    override fun getViewModel(): MainViewModel {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        return mViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel

    }

}
