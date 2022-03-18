package and.okm.currency.rate.presentation.screens.favorites

import and.okm.currency.rate.R
import and.okm.currency.rate.databinding.FavoritesFragmentBinding
import and.okm.currency.rate.utils.ErrorHandler
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var adapter: FavoritesCurrenciesAdapter

    private val viewModel by viewModels<FavoritesViewModel>()

    private lateinit var binding: FavoritesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoritesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onDeleteClicked: (currency: String) -> Unit = { currency ->
            viewModel.changeFavoriteCurrencies(currency)
        }

        adapter = FavoritesCurrenciesAdapter(
            onDeleteClicked = onDeleteClicked
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
            viewModel.getAllFavoriteCurrenciesRates()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.contentIsEmpty.collect {
                binding.hintText.text = context?.resources?.getString(R.string.text_hint) ?: ""
                binding.hintText.isVisible = !it
                binding.deleteAllButton.isVisible = it
            }
        }

        binding.deleteAllButton.setOnClickListener {
            viewModel.deleteAllFavoriteCurrencies()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect {
                ErrorHandler.showAlertDialog(
                    context = requireContext(),
                    message = it,
                    onPositiveButtonClicked = { viewModel.getAllFavoriteCurrenciesRates() }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllFavoriteCurrenciesRates()
    }

}