package com.example.imagefilters.activities.editimage

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.imagefilters.activities.main.MainActivity
import com.example.imagefilters.databinding.ActivityEditImageBinding
import com.example.imagefilters.utilities.displayToast
import com.example.imagefilters.utilities.show
import com.example.imagefilters.viewmodels.EditImageViewModel

class EditImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditImageBinding

    private val viewModel: EditImageViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setupObservers()
        prepareImagePreview()
    }
    private fun setupObservers(){
        viewModel.imagePreviewUiState.observe(this,{
            val dataState = it?: return@observe
            binding.previewProgressBar.visibility =
                if (dataState.isLoading) View.VISIBLE else View.GONE
            dataState.bitmap?.let { bitmap ->
                binding.imagePreview.setImageBitmap(bitmap)
                binding.imagePreview.show()
            } ?: kotlin.run{
                dataState.error?.let {  error->
                    displayToast(error)
                    }
            }
        })
    }
    private fun prepareImagePreview(){
      intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let {
          imageUri ->
          viewModel.prepareImagePreview(imageUri)
      }
    }
//    private fun displayImagePreview() {
//        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let {
//            val inputStream = contentResolver.openInputStream(it)
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//            binding.imagePreview.setImageBitmap(bitmap)
//            binding.imagePreview.visibility = View.VISIBLE
//
//        }
//
//    }

    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
    }
}