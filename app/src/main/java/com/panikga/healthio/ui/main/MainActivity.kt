package com.panikga.healthio.ui.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.panikga.healthio.ui.main.history.HistoryFragment
import com.panikga.healthio.ui.main.home.HomeFragment
import com.panikga.healthio.ui.main.profile.ProfileFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.panikga.healthio.R
import com.panikga.healthio.bpjsvsbukan.ImageClassifierActivity
import com.panikga.healthio.databinding.ActivityMainBinding
import com.panikga.healthio.ui.authentication.forgotpassword.ForgotPasswordActivity


class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.setItemSelected(R.id.navigation_home, true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment()).commit()

        bottomMenu()
    }

    private fun bottomMenu() {
        binding.navView.setOnItemSelectedListener(object :
            ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                var fragment: Fragment? = null
                when (id) {
                    R.id.navigation_home -> fragment = HomeFragment()
                    R.id.navigation_history -> fragment = HistoryFragment()
                    R.id.navigation_profile -> fragment = ProfileFragment()
                }
                if (fragment != null) {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragment_container, fragment
                        ).commit()
                }
            }
        })
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
            runTextRecognition(imageBitmap)
        }
    }

    private fun runTextRecognition(mSelectedImage: Bitmap) {
        Log.d("MLKIT: ", "JALAN")
        val image = InputImage.fromBitmap(mSelectedImage, 0)
        val recognizer = TextRecognition.getClient()
        recognizer.process(image)
            .addOnSuccessListener { texts ->
                for (block in texts.textBlocks) {
//                    val boundingBox = block.boundingBox
//                    val cornerPoints = block.cornerPoints
                    val text = block.text

                    for (line in block.lines) {
                        for (element in line.elements) {
                            Log.d("MLKIT: ", element.text)
                        }

                    }
                }
//                Toast.makeText(this, texts, Toast.LENGTH_SHORT).show()
//                processTextRecognitionResult(texts)
//                Log.d("MLKIT: ", texts.toString())
            }
            .addOnFailureListener { e -> // Task failed with an exception
                e.printStackTrace()
            }
    }

    fun fabClickedCallback(view: View) {
        val intent = Intent(this@MainActivity, ImageClassifierActivity::class.java)
        startActivity(intent)
//        dispatchTakePictureIntent()
    }

//    private fun processTextRecognitionResult(texts: Text) {
//        val blocks: List<Text.TextBlock> = texts.textBlocks
//        if (blocks.size == 0) {
//            Toast.makeText(this, "No text found", Toast.LENGTH_SHORT).show()
//            return
//        }
//        Log.d("MLKIT: ", blocks.t)
////        for (i in blocks.indices) {
////            val lines: List<Text.Line> = blocks[i].lines
////            for (j in lines.indices) {
////                val elements: List<Text.Element> = lines[j].elements
////                for (k in elements.indices) {
////                    Toast.makeText(this, elements[k].toString(), Toast.LENGTH_SHORT).show()
//////                    val textGraphic: Graphic = TextGraphic(mGraphicOverlay, elements[k])
//////                    mGraphicOverlay.add(textGraphic)
////                }
////            }
////        }
//    }

}