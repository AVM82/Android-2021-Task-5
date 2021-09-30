package org.rsschool.android2021task5.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.rsschool.android2021task5.databinding.DetailFragmentBinding
import org.rsschool.android2021task5.model.ImageDTO

private const val ARG_IMAGE = "item"

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val binding get() = requireNotNull(_binding)
    private var image: ImageDTO? = null
    private var _binding: DetailFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DetailFragmentBinding.inflate(inflater).also { _binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image = arguments?.getParcelable(ARG_IMAGE)
    }

    companion object {
        @JvmStatic
        fun newInstance(image: ImageDTO) =
            DetailFragment().apply {
                arguments = bundleOf(
                    ARG_IMAGE to image
                )
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
