package org.rsschool.android2021task5.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.rsschool.android2021task5.R
import org.rsschool.android2021task5.databinding.MainFragmentBinding
import org.rsschool.android2021task5.model.ImageDTO
import org.rsschool.android2021task5.ui.adapter.ImagesAdapter
import org.rsschool.android2021task5.ui.adapter.ImagesLoadStateAdapter
import org.rsschool.android2021task5.ui.detail.DetailFragment

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val binding get() = requireNotNull(_binding)
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        ImagesAdapter(ImagesAdapter.OnClickListener { renderDetailFragment(it) })
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

    private fun renderDetailFragment(image: ImageDTO) {
        parentFragmentManager.commit {
            addToBackStack("detailFragment")
            replace(R.id.container, DetailFragment.newInstance(image))
        }
    }

    private fun <T> views(block: MainFragmentBinding.() -> T) = binding.block()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
