package com.example.imagefilters.activities.main
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.imagefilters.activities.editimage.EditImageActivity
import com.example.imagefilters.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){}
    companion object{
        const val KEY_IMAGE_URI ="imageUri"
    }


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        val intent = Intent(this, EditImageActivity::class.java).also {
            editImageIntent ->
            editImageIntent.putExtra(KEY_IMAGE_URI, uri)
        }
        resultLauncher.launch(intent)
    }
    private fun setListeners(){
        binding.btnEditNewImage.setOnClickListener {
             getContent.launch("image/*")
        }
    }


}