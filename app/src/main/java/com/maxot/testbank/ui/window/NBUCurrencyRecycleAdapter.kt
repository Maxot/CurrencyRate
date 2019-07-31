package com.maxot.testbank.ui.window

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxot.R
import com.maxot.databinding.ItemCurrencyNbuBinding
import com.maxot.databinding.ItemCurrencyPrivatBinding
import com.maxot.testbank.data.model.ExchangeRate
import com.maxot.testbank.data.model.NBUCurrency
import com.maxot.testbank.interfaces.OnClickListener
import java.text.DecimalFormat
import java.text.FieldPosition

class NBUCurrencyRecycleAdapter(private  var items: List<NBUCurrency>, var selectedItemPosition: Int) : RecyclerView.Adapter<NBUCurrencyRecycleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyNbuBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(items[position], position, selectedItemPosition)
    }


    fun getPositionByName(name: String): Int{
        var pos = -1
        for (i in items.indices){
            if (name == items[i].cc)
                pos = i
        }

        notifyItemChanged(selectedItemPosition)
        selectedItemPosition = pos
        notifyItemChanged(selectedItemPosition)
        return pos
    }



    class ViewHolder(var binding: ItemCurrencyNbuBinding):
            RecyclerView.ViewHolder(binding.root) {

        fun bind(data: NBUCurrency, pos: Int, selectedItemPos: Int?) {
            var rate = data.rate
            var count = 1
            while (rate < 1) {
                count *= 10
                rate *= count
            }

            val decimalFormat = DecimalFormat("#.##")

            binding.data = data
            binding.rate = decimalFormat.format(rate)
            binding.count = count

            if (pos % 2 == 0){
                binding.frame.background = binding.frame.resources.getDrawable(R.color.white)
            } else {
                binding.frame.background = binding.frame.resources.getDrawable(R.color.gray)
            }

            if (pos == selectedItemPos){
                binding.frame.background = binding.frame.resources.getDrawable(R.color.green)
            } else {
                binding.frame.background = binding.frame.resources.getDrawable(R.color.white)
            }

        }
    }
}