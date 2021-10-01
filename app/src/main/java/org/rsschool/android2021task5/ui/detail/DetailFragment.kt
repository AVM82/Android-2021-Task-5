package org.rsschool.android2021task5.ui.detail

import android.Manifest
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context.DOWNLOAD_SERVICE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import dagger.hilt.android.AndroidEntryPoint
import org.rsschool.android2021task5.R
import org.rsschool.android2021task5.databinding.DetailFragmentBinding
import org.rsschool.android2021task5.helper.PermissionHelper
import org.rsschool.android2021task5.helper.getDefaultRequestOptions
import org.rsschool.android2021task5.model.ImageDTO
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer

private const val ARG_IMAGE = "item"

@AndroidEntryPoint
class DetailFragment : Fragment() {

    interface PermissionListener {
        fun onGranted(isGranted: Boolean)
    }

    private val binding get() = requireNotNull(_binding)
    private val requestPermissionLauncher = PermissionHelper().

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

    private fun saveIMG(detailImage: ImageView) {
        when (detailImage.drawable) {
            is GifDrawable -> saveGif((detailImage.drawable as GifDrawable).buffer)
            is BitmapDrawable -> saveBitmap((detailImage.drawable as BitmapDrawable).bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher.launch(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image = arguments?.getParcelable(ARG_IMAGE)
        views {
            toolBar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
            saveButton.setOnClickListener {

                    PermissionHelper().permissionRequest(this@DetailFragment) {
                        saveIMG(detailImage)
                    }


//                when (PackageManager.PERMISSION_GRANTED) {
//                    ContextCompat.checkSelfPermission(
//                        requireContext(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ) -> {
//                        saveIMG()
//                    }
//                    else -> {
//                        // You can directly ask for the permission.
//                        // The registered ActivityResultCallback gets the result of this request.
//                        requestPermissionLauncher.launch(
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        )
//                    }
//                }


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

    private fun saveGif(buffer: ByteBuffer) {
        val filename = "${System.currentTimeMillis()}.gif"
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val gifFile = File(imagesDir, filename)

        val output = FileOutputStream(gifFile)
        val bytes = ByteArray(buffer.capacity())
        (buffer.duplicate().clear() as ByteBuffer).get(bytes)
        output.write(bytes, 0, bytes.size)
        output.close()
    }



    private fun save(image: ImageDTO?) {
        val request = DownloadManager.Request(Uri.parse(image?.url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle("Voyager")
            .setDescription("File is downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "fileName")
        val downloadManager = activity?.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(request)

    }


//    fun isStoragePermissionGranted(): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED
//            ) {
//                //Permission is granted
//                true
//            } else {
//                //Permission is revoked
//                ActivityCompat.requestPermissions(
//                    requireActivity(),
//                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                    1
//                )
//                false
//            }
//        } else {
//            //permission is automatically granted on sdk<23 upon installation
//            //Permission is granted
//            true
//        }
//    }


    private fun saveBitmap(bitmap: Bitmap) {
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
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
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
