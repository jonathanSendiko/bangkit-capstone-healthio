package com.panikga.healthio.bpjsvsbukan

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.panikga.healthio.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

class ImageClassifierActivity : AppCompatActivity() {

    private val mInputSize = 150
    private val mModelPath = "model.tflite"
    private val mLabelPath = "label.txt"
    private lateinit var classifier: Classifier

    private lateinit var imgView: ImageView
    private lateinit var currentPhotoPath: String

    private var namaRs: String = ""
    private var noBPJS: String = ""
    private var diagnosis: String = ""
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_classifier)
        initClassifier()
        initViews()
    }

    private fun initClassifier() {
        classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)
    }

    private fun initViews() {
        imgView = findViewById(R.id.test)
        dispatchTakePictureIntent()
    }

    private val REQUEST_IMAGE_CAPTURE = 1
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.panikga.healthio.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val file = File(currentPhotoPath)
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))
            if (bitmap != null) {
                runImageValidation(bitmap)
            }
        }
    }

    private fun runImageValidation(image: Bitmap){
        val result = classifier.recognizeImage(image)
        val validity = result[0].title
        if (validity == "bpjs"){
            runOnUiThread { Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show() }
            val newBitmap = crop(image)
            imgView.setImageBitmap(newBitmap)
            runTextRecognition(newBitmap!!)
        }
        else{
            Toast.makeText(this, "foto tidak valid", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun crop(originalBitmap: Bitmap): Bitmap? {
        return Bitmap.createBitmap(originalBitmap, 0, (originalBitmap.height / 3), originalBitmap.width / 2, originalBitmap.height / 2)
    }

    private fun runTextRecognition(mSelectedImage: Bitmap) {
        Log.d("MLKIT: ", "JALAN")
        val image = InputImage.fromBitmap(mSelectedImage, 0)
        val recognizer = TextRecognition.getClient()
        recognizer.process(image)
            .addOnSuccessListener { texts ->
                for (block in texts.textBlocks) {
                    for (line in block.lines) {
                        Log.d("MLKIT: ", line.text)
                        counter += 1
                        when (counter) {
                            4 -> {
                                namaRs = line.text
                            }
                            9 -> {
                                noBPJS = line.text
                            }
                            11 -> {
                                diagnosis = line.text
                            }
                        }
                    }
                }
                Log.d("hasil", "RS = $namaRs BPJS = $noBPJS Diagnosis = $diagnosis")
                // TODO: 6/1/2021 INTENT KE PESEN OTOMATIS
            }
            .addOnFailureListener { e -> // Task failed with an exception
                e.printStackTrace()
            }
    }

}