package and.okm.currency.rate.presentation.screens.settings

import and.okm.currency.rate.databinding.SettingsFragmentBinding
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
class SettingsFragment : Fragment() {

    private val viewModel by viewModels<SettingsViewModel>()

    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.alphabeticalSorting.collect {
                binding.alphabeticalSorting.isChecked = it
                binding.valueSorting.isChecked = !it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.ascendingOrder.collect {
                binding.ascendingOrder.isChecked = it
                binding.descendingOrder.isChecked = !it
            }
        }

        binding.alphabeticalSorting.setOnCheckedChangeListener { _, value ->
            viewModel.setAlphabeticalSortingSetting(value)
        }

        binding.ascendingOrder.setOnCheckedChangeListener { _, value ->
            viewModel.setAscendingOrderSetting(value)
        }

        viewModel.getSortingSettings()
    }

}