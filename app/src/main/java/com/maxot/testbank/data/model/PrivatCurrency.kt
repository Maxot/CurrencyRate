package com.maxot.testbank.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrivatCurrency(
    val bank: String,
    val baseCurrency: Int,
    val baseCurrencyLit: String,
    val date: String,
    val exchangeRate: List<ExchangeRate>
) : Parcelable

@Parcelize
data class ExchangeRate(
    val baseCurrency: String,
    val currency: String,
    val purchaseRate: Double?,
    val purchaseRateNB: Double,
    val saleRate: Double?,
    val saleRateNB: Double
) : Parcelable