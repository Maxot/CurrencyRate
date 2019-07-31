package com.maxot.testbank.ui.window

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxot.R
import com.maxot.databinding.ItemCurrencyPrivatBinding
import com.maxot.testbank.data.model.ExchangeRate
import com.maxot.testbank.interfaces.OnClickListener
import java.text.FieldPosition

class PrivatCurrencyRecycleAdapter(private  var items: List<ExchangeRate>, private val listener: OnClickListener<String>) : RecyclerView.Adapter<PrivatCurrencyRecycleAdapter.ViewHolder>() {

    var selectedItemPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyPrivatBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(items[position], position, selectedItemPosition)

        holder.binding.frame.setOnClickListener{
            notifyItemChanged(selectedItemPosition)
            selectedItemPosition = position
            notifyItemChanged(selectedItemPosition)

            listener.onClick(items[position].currency)
        }
    }

    fun setSelectedPosition(pos: Int){
        if (pos != -1) {
            notifyItemChanged(selectedItemPosition)
            selectedItemPosition = pos
            notifyItemChanged(selectedItemPosition)

            listener.onClick(items[pos].currency)
        }
    }



    class ViewHolder(var binding: ItemCurrencyPrivatBinding):
            RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ExchangeRate, pos: Int, selectedItemPos: Int) {
            binding.data = data
            if (pos == selectedItemPos){
                binding.frame.background = binding.frame.resources.getDrawable(R.color.green)
            } else {
                binding.frame.background = binding.frame.resources.getDrawable(R.color.white)
            }

        }
    }
}