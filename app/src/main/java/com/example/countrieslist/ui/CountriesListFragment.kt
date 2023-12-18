package com.example.countrieslist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.countrieslist.ui.CountriesListViewModel
import com.example.countrieslist.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesListFragment : Fragment() {

    private val viewModel by viewModels<CountriesListViewModel>()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater)
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        binding?.countryListAdapter = CountryListAdapter()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCountriesList().observe(viewLifecycleOwner) { pagingData ->
                Log.d(TAG, "OnViewCreated: pagingData $pagingData")
                binding?.countryListAdapter?.submitData(lifecycle, pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = CountriesListFragment::class.qualifiedName
    }
}