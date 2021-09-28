package org.rsschool.android2021task5.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.rsschool.android2021task5.databinding.MainFragmentBinding
import org.rsschool.android2021task5.ui.adapter.ImagesAdapter

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        ImagesAdapter()
    }
    private var _binding: MainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MainFragmentBinding.inflate(inflater).also { _binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views {
            itemList.adapter = adapter
        }

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.imagesFlow.collectLatest { pagingData -> adapter.submitData(pagingData) }
        }
    }

    private fun <T> views(block: MainFragmentBinding.() -> T) = binding.block()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
