package com.example.imagefilters.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagefilters.repositories.EditImageRepository
import com.example.imagefilters.utilities.Coroutines

class EditImageViewModel(private val editImageRepository: EditImageRepository) : ViewModel() {

    private val imagePreViewDataState = MutableLiveData<ImagePreViewDataState>()
    val imagePreviewUiState : LiveData<ImagePreViewDataState> get() = imagePreViewDataState

    fun prepareImagePreview(imageUri : Uri){
        Coroutines.io {
            runCatching {
            emitImagePreviewUiState(isLoading = true)
            editImageRepository.prepareImagePreview(imageUri)
        }.onSuccess {  bitmap ->
        if(bitmap != null){
            emitImagePreviewUiState(bitmap = bitmap)
        }else{
            emitImagePreviewUiState(error = "Unable to prepare image preview")
        }
            }.onFailure {
                emitImagePreviewUiState(error = it.message.toString())
            }
    }}

    private fun emitImagePreviewUiState(isLoading : Boolean = false,
                                        bitmap: Bitmap? = null,
                                        error : String? = null){
        val dataState = ImagePreViewDataState(isLoading,bitmap,error)
        imagePreViewDataState.postValue(dataState)

    }



    data class ImagePreViewDataState(
        val isLoading : Boolean,
        val bitmap: Bitmap?,
        val error : String?

    )
}