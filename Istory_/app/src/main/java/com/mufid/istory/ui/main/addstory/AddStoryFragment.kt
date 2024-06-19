package com.mufid.istory.ui.main.addstory

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.viewModels
import com.mufid.istory.ui.main.MainViewModel
import com.mufid.istory.utils.uriToFile
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mufid.istory.utils.createTempFile
import com.mufid.istory.utils.reduceFileImage
import com.mufid.istory.utils.rotateBitmap
import com.mufid.istory.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mufid.istory.R
import com.mufid.istory.databinding.FragmentAddStoryBinding
import com.mufid.istory.databinding.LayoutBottomsheetBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddStoryFragment : Fragment() {

    private lateinit var currentPhotoPath: String
    private lateinit var navView: BottomNavigationView
    private lateinit var factory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { factory }
    private var _binding: FragmentAddStoryBinding? = null
    private val binding get() = _binding
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddStoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        factory = ViewModelFactory.getInstance(requireActivity())
        navView = requireActivity().findViewById(R.id.bottom_nav)
        navView.visibility = View.GONE
        binding?.apply {
            imgUpload.setOnClickListener {
                setUpBottomSheet()
            }
            btnUpload.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                uploadImage()
            }
            btnUpload.isEnabled = false
        }
    }

    private fun setUpBottomSheet() {
        val view = LayoutBottomsheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireActivity()).apply {
            setContentView(view.root)
        }
        dialog.show()

        view.tvGaleri.setOnClickListener {
            dialog.dismiss()
            getImageGallery.launch("image/*")
        }

        view.tvKamera.setOnClickListener {
            dialog.dismiss()
            startTakePhoto()
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createTempFile(requireActivity().application).also {
            val photoUri: Uri = FileProvider.getUriForFile(
                requireActivity(),
                "com.mufid.istory.provider",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            getImageCamera.launch(intent)
        }
    }

    private val getImageGallery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        it?.let { selectedImage ->
            getFile = uriToFile(selectedImage, requireActivity())
        }

        binding?.apply {
            it?.let { uri ->
                imgUpload.setImageURI(uri)
                tvPilihGambar.visibility = View.GONE
                btnUpload.isEnabled = true
            }
        }
    }

    private val getImageCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            getFile = File(currentPhotoPath)
            binding?.apply {
                btnUpload.isEnabled = true
                imgUpload.setImageBitmap(getCapturedImage())
                tvPilihGambar.visibility = View.GONE
            }
        }
    }

    private fun getCapturedImage(): Bitmap {
        val exifInterface = ExifInterface(currentPhotoPath)
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        val bitmap = BitmapFactory.decodeFile(getFile?.path)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotateBitmap(bitmap, 90f)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotateBitmap(bitmap, 180f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotateBitmap(bitmap, 270f)
            }
            else -> bitmap
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val textDesc = binding?.etDeskripsi?.text.toString().trim()
            val description: RequestBody = textDesc.toRequestBody("text/plain".toMediaType())
            viewModel.getUserToken().observe(viewLifecycleOwner) {
                val token = "Bearer $it"
                viewModel.uploadStory(token, imageMultiPart, description).observe(viewLifecycleOwner) { response ->
                    binding?.progressBar?.visibility = View.GONE
                    if (response.error as Boolean) {
                        Toast.makeText(requireActivity(), "Terjadi kesalahan ${response.message}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireActivity(), "${response.message}", Toast.LENGTH_SHORT).show()
                        requireActivity().onBackPressed()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navView.visibility = View.VISIBLE
        _binding = null
    }
}