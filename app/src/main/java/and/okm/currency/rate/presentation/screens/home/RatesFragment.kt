package and.okm.currency.rate.presentation.screens.home

import and.okm.currency.rate.databinding.RatesFragmentBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatesFragment : Fragment() {

    private lateinit var adapter: RatesAdapter

    private val viewModel by viewModels<RatesViewModel>()

    private lateinit var binding: RatesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RatesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onStarClicked: (currency: String) -> Unit = { currency ->
            viewModel.changeFavoriteCurrencies(currency)
        }

        adapter = RatesAdapter(
            onStarClicked = onStarClicked
        )

        binding.recyclerview.adapter = adapter

        viewModel.rates.observe(viewLifecycleOwner) {
            adapter.setRates(it)
        }

        viewModel.refreshStatus.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getAllRates()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllRates()
    }

}