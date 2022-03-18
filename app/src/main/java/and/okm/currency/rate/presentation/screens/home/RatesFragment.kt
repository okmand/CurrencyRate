package and.okm.currency.rate.presentation.screens.home

import and.okm.currency.rate.databinding.RatesFragmentBinding
import and.okm.currency.rate.utils.ErrorHandler
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.rates.collect {
                adapter.setRates(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.refreshStatus.collect {
                binding.swipeRefresh.isRefreshing = it
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getAllRates()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect {
                ErrorHandler.showAlertDialog(
                    context = requireContext(),
                    message = it,
                    onPositiveButtonClicked = { viewModel.getAllRates() }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllRates()
    }

}