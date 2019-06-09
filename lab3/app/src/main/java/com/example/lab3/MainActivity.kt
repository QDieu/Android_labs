package com.example.lab3

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    var name: String =""
    var PhotoPath : String = ""

    companion object {
        val KEY_NAME = "key_name"
        val KEY_PATH = "key_path"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{v ->
            if(editText.text.isEmpty())
                Snackbar.make(v, R.string.textNameNone, Snackbar.LENGTH_LONG).show()
            else{
                name = editText.text.toString()
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{ takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also{
                        val photoFile: File? = try{
                            createImage()
                        }catch (ex: IOException){
                            Snackbar.make(v, R.string.makePhotoError, Snackbar.LENGTH_LONG).show()
                            null
                        }
                        photoFile.also{
                            val photoURI: Uri = FileProvider.getUriForFile(
                                this,
                                "com.example.app.fileprovider",
                                photoFile!!
                            )
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                        }
                    }

                }
            }
        }
    }
    @Throws(IOException::class)
    private fun createImage(): File{
        val imageTime: String = SimpleDateFormat("ddMMyyyy").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${imageTime}_",
            ".jpg",
            storageDir
        ).apply{PhotoPath = absolutePath}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val intentTwo = Intent(applicationContext, ActivityByImage::class.java)

            intentTwo.putExtra(ActivityByImage.IMAGE_KEY, PhotoPath)
            intentTwo.putExtra(ActivityByImage.NAME_KEY, name)
            startActivity(intentTwo)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putString(KEY_NAME, name)
        outState.putString(KEY_PATH, PhotoPath)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        name = savedInstanceState!!.getString(KEY_NAME).orEmpty()
        PhotoPath = savedInstanceState.getString(KEY_PATH).orEmpty()
    }
}
