package org.rsschool.android2021task5.ui.detail

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import org.rsschool.android2021task5.R
import org.rsschool.android2021task5.databinding.DetailFragmentBinding
import org.rsschool.android2021task5.helper.getDefaultRequestOptions
import org.rsschool.android2021task5.model.ImageDTO
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

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
        views {
            toolBar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
            saveButton.setOnClickListener {

                var bitmap =
                    Bitmap.createBitmap(detailImage.width, detailImage.height, Bitmap.Config.ARGB_8888)
                var canvas = Canvas(bitmap)
                detailImage.draw(canvas)

                saveMediaToStorage(bitmap)


            }
            image?.let {
                widthValueText.text = getString(R.string.height, it.width.toString())
                heightValueText.text = getString(R.string.width, it.height.toString())
                Glide
                    .with(this@DetailFragment)
                    .load(it.url)
                    .apply(
                        getDefaultRequestOptions()
                    ).into(detailImage)
            } ?: detailImage.setImageResource(R.drawable.ic_baseline_cloud_download_24)
        }
    }


    //the function I already explained, it is used to save the Bitmap to external storage
    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.gif"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context?.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/gif")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
//        fos?.use {
        val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        fos?.write(stream.toByteArray())
//        }
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

    private fun <T> views(block: DetailFragmentBinding.() -> T) = binding.block()
}
