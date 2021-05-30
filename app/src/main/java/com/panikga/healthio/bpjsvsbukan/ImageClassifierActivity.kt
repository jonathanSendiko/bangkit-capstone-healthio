package com.panikga.healthio.bpjsvsbukan

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.panikga.healthio.R

class ImageClassifierActivity : AppCompatActivity() {

    private val mInputSize = 150
    private val mModelPath = "model.tflite"
    private val mLabelPath = "label.txt"
    private lateinit var classifier: Classifier

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
        // TODO: 5/30/2021 IMPLEMENTASI KAMERA
        dispatchTakePictureIntent()
    }


    val REQUEST_IMAGE_CAPTURE = 1
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            e.toString()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            runImageValidation(imageBitmap)
        }
    }

    private fun runImageValidation(image: Bitmap){
        val result = classifier.recognizeImage(image)

        runOnUiThread { Toast.makeText(this, result.get(0).title, Toast.LENGTH_SHORT).show() }
    }

//    private fun runTextRecognition(mSelectedImage: Bitmap) {
//        Log.d("MLKIT: ", "JALAN")
//        val image = InputImage.fromBitmap(mSelectedImage, 0)
//        val recognizer = TextRecognition.getClient()
//        recognizer.process(image)
//            .addOnSuccessListener { texts ->
//                for (block in texts.textBlocks) {
////                    val boundingBox = block.boundingBox
////                    val cornerPoints = block.cornerPoints
//                    val text = block.text
//
//                    for (line in block.lines) {
//                        for (element in line.elements) {
//                            Log.d("MLKIT: ", element.text)
//                        }
//
//                    }
//                }
////                Toast.makeText(this, texts, Toast.LENGTH_SHORT).show()
////                processTextRecognitionResult(texts)
////                Log.d("MLKIT: ", texts.toString())
//            }
//            .addOnFailureListener { e -> // Task failed with an exception
//                e.printStackTrace()
//            }
//    }


}