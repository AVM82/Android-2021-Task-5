package org.rsschool.android2021task5.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.rsschool.android2021task5.databinding.MainFragmentBinding
import org.rsschool.android2021task5.ui.adapter.ImagesAdapter
import org.rsschool.android2021task5.ui.adapter.ImagesLoadStateAdapter

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
            itemList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ImagesLoadStateAdapter { adapter.retry() },
                footer = ImagesLoadStateAdapter { adapter.retry() }
            )
            retryButton.setOnClickListener { adapter.retry() }
            adapter.addLoadStateListener { state ->
                itemList.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
                noConnectImage.isVisible = state.refresh is LoadState.Error
                retryButton.isVisible = state.refresh is LoadState.Error
            }
        }

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.imagesFlow.collectLatest { pagingData ->
                run {
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    private fun <T> views(block: MainFragmentBinding.() -> T) = binding.block()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
