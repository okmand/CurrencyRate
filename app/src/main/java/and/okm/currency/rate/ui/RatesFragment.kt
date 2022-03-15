package and.okm.currency.rate.ui

import and.okm.currency.rate.databinding.RatesFragmentBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RatesFragment : Fragment() {

    @Inject
    lateinit var adapter: RatesAdapter

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
        binding.recyclerview.adapter = adapter

        viewModel.rates.observe(viewLifecycleOwner) {
            adapter.setRates(it)
        }

        viewModel.progressBarStatus.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        }

        viewModel.getAllRates()
    }


    companion object {
        fun newInstance() = RatesFragment()
    }

}