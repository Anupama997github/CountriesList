package com.example.countrieslist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.countrieslist.databinding.ListViewItemBinding
import com.example.countrieslist.mvvm.model.Country

class CountryListAdapter :
    PagingDataAdapter<Country, CountryListAdapter.CountryViewHolder>(DiffCallback) {

    class CountryViewHolder(
        private var binding: ListViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.countryList = country
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.name == newItem.name
                    && oldItem.code == newItem.code
                    && oldItem.capital == newItem.capital
                    && oldItem.region == newItem.region
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        return CountryViewHolder(
            ListViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
