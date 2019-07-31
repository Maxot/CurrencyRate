package com.maxot.testbank.ui.window

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxot.BR
import com.maxot.R
import com.maxot.databinding.FragmentCurrencyBinding
import com.maxot.testbank.MainActivity
import com.maxot.testbank.data.model.ExchangeRate
import com.maxot.testbank.data.model.NBUCurrency
import com.maxot.testbank.data.model.PrivatCurrency
import com.maxot.testbank.interfaces.OnClickListener
import com.maxot.testbank.ui.base.BaseActivity
import com.maxot.testbank.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_currency.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext


class CurrencyFragment : BaseFragment<CurrencyViewModel, FragmentCurrencyBinding>(), OnClickListener<String> {

    val CALENDAR = "calendar"
    val PRIVAT_DATA = "privat_data"
    val NBU_DATA = "nbu_data"
    val SELECTED_POSITION = "selected_position"

    lateinit var adapter: PrivatCurrencyRecycleAdapter
    lateinit var adapterNbu: NBUCurrencyRecycleAdapter
    var calendar = Calendar.getInstance()

    val sdf = SimpleDateFormat("dd.MM.yyyy")
    val sdfNbu = SimpleDateFormat("yyyyMMdd")

    lateinit var currencyPrivatData : List<ExchangeRate>
    lateinit var currencyNBUData : List<NBUCurrency>
    var selectedPosition  = -1



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            it.getSerializable(CALENDAR)?.let {
                calendar = it as Calendar
            }

            it.get(PRIVAT_DATA)?.let {
                currencyPrivatData = it as List<ExchangeRate>
            }

            it.get(NBU_DATA)?.let {
                currencyNBUData = it as List<NBUCurrency>
            }

            selectedPosition = it.getInt(SELECTED_POSITION)
        }

        mViewDataBinding.date.text = sdf.format(calendar.time)

        if (this::currencyPrivatData.isInitialized){
            setPrivatDataRecycler(currencyPrivatData)
        } else {
            getPrivatCurrency(sdf.format(calendar.time))
        }


        if (this::currencyNBUData.isInitialized){
            setNBUDataRecycler(currencyNBUData)
        } else {
            getNBUCurrency(sdfNbu.format(calendar.time))
        }




        mViewModel.currencyPrivatData.observe(this, Observer {
            if (!it?.exchangeRate.isNullOrEmpty()) {

                val data = ArrayList<ExchangeRate>()
                it?.exchangeRate?.let {
                    for (item in it) {
                        if (item.purchaseRate != null && item.saleRate != null)
                            data.add(item)
                    }
                }
                currencyPrivatData = data
                no_data.visibility = View.GONE
                privat_recycler.visibility = View.VISIBLE
                setPrivatDataRecycler(data)
            } else {
                no_data.visibility = View.VISIBLE
                privat_recycler.visibility = View.GONE
            }

            progress_bar_privat.visibility = View.GONE
        })


        mViewModel.currencyNBUData.observe(this, Observer {
            if (it.isNotEmpty()) {
                no_data_nbu.visibility = View.GONE
                recycler_nbu.visibility = View.VISIBLE

                currencyNBUData = it
                setNBUDataRecycler(it)
                showProgressBar(false)
            } else {
                no_data_nbu.visibility = View.VISIBLE
                recycler_nbu.visibility = View.GONE

            }
            progress_bar_nbu.visibility = View.GONE

        })



        date.setOnClickListener {
            setDate()
        }
    }

    fun setPrivatDataRecycler(data: List<ExchangeRate>){
        adapter = PrivatCurrencyRecycleAdapter(data, this)
        mViewDataBinding.privatRecycler.layoutManager = LinearLayoutManager(context)
        mViewDataBinding.privatRecycler.adapter = adapter

    }

    fun setNBUDataRecycler(data: List<NBUCurrency>){
        adapterNbu = NBUCurrencyRecycleAdapter(data, -1)
        mViewDataBinding.recyclerNbu.layoutManager = LinearLayoutManager(context)
        mViewDataBinding.recyclerNbu.adapter = adapterNbu

        if (this::adapter.isInitialized)
        adapter.setSelectedPosition(selectedPosition)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(CALENDAR, calendar)

        if (this::currencyPrivatData.isInitialized)
        outState.putParcelableArrayList(PRIVAT_DATA, currencyPrivatData as java.util.ArrayList<out Parcelable>)

        if (this::currencyPrivatData.isInitialized)
            outState.putParcelableArrayList(NBU_DATA, currencyNBUData as java.util.ArrayList<out NBUCurrency>)

        outState.putInt(SELECTED_POSITION, adapter.selectedItemPosition)
    }

    override fun onClick(param: String) {
        if (this::adapterNbu.isInitialized)
        adapterNbu.getPositionByName(param).let {
            mViewDataBinding.recyclerNbu.scrollToPosition(adapterNbu.getPositionByName(param))
        }
    }

    private fun getPrivatCurrency(data: String){
        progress_bar_privat.visibility = View.VISIBLE
        mViewModel.getPrivatCurrency(data)
    }

    private fun getNBUCurrency(data: String){
        progress_bar_nbu.visibility = View.VISIBLE
        mViewModel.getNBUCurrency(data)
    }


    // отображаем диалоговое окно для выбора даты
    private fun setDate() {
        DatePickerDialog(context!!,
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->

                    calendar.set(year, monthOfYear, dayOfMonth)
                    val datePrivat = "${dayOfMonth}.${monthOfYear + 1}.${year}"

                    mViewDataBinding.date.text = sdf.format(calendar.time)

                    getPrivatCurrency(datePrivat)


                    val montForNbu = if (monthOfYear + 1 < 10 )  "0${monthOfYear + 1}" else monthOfYear + 1
                    val dateNbu = "${year}${montForNbu}${dayOfMonth}"
                    getNBUCurrency(dateNbu)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show()
    }




    override fun getLayoutId(): Int {
        return R.layout.fragment_currency
    }

    override fun getViewModel(): CurrencyViewModel {
        mViewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        return mViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

}