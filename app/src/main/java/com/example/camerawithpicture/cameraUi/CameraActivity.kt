package com.example.camerawithpicture.cameraUi

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.camerawithpicture.R
import com.example.camerawithpicture.application.MyApplication
import com.example.camerawithpicture.base.BaseActivity
import com.example.camerawithpicture.data.MyData
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : BaseActivity() {
    private var pathToFile: String? = null
    private var videoPathToFile: String? = null
    override fun setLayout(): Int {
        return R.layout.activity_camera
    }

    override fun initView(savedInstanceState: Bundle?) {
        setClickListener()
        Log.d("onCreate", "OnCreateCalled")
        if (savedInstanceState==null){
            profileSetImage.setImageResource(R.drawable.placeholder)
        }
        profileSetImage.setImageURI(Uri.parse(MyData.getimage()))
    }

    private fun setClickListener() {
        if (Build.VERSION.SDK_INT >= 23) {
            /**here it will checks the build version i.e., below versions that
             * if doesn't contain then it will add the permissions to the manifest*/
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 2
            )
        }
        openCamera.setOnClickListener {
            selectAction()
        }
    }

    private fun selectAction() {
        /**here below code represents the both to take and select images
         * from gallery and takes from camera app*/
        val options = arrayOf(
            "Take Photo",
            "Choose Photo from Gallery",
            "Take Video",
            "Choose Video from Gallery",
            "Cancel"
        )
        val builder = AlertDialog.Builder(this@CameraActivity)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" ->
                    /** here we describe to take photo in multiple ways by showing alertDailog
                     * here below code represents to take photo from camera app*/
                    captureImage()
                /**here below code represents to select photo from gallery*/
                options[item] == "Choose Photo from Gallery" ->
                    /**here images choose from gallery*/
                    chooseImageFromGallery()
                options[item] == "Take Video" ->
                    /**here below code is for to record video using system or other app and stores as mp4 file*/
                    captureVideo()
                options[item] == "Choose Video from Gallery" ->
                    /**here video is selected from gallery*/
                    chooseVideoFromGallery()
                options[item] == "Cancel" -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun captureImage() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        /**here it is the INTENT ACTION to take photo*/
        if (cameraIntent.resolveActivity(packageManager) != null) {
            val photoFile: File? = createPhotoFile()
            /**here we must create the photo as a file*/
            if (photoFile != null) {
                pathToFile = photoFile.absolutePath
               val photoUri = FileProvider.getUriForFile(
                    this@CameraActivity,
                    "com.example.camerawithpicture.fileprovider",
                    photoFile

                )
                Log.d("uri", "PhotoUri is $photoUri")
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(cameraIntent, 1)

                MyData.setimage(photoUri.toString())
            }
        }
    }

    private fun chooseImageFromGallery() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, 2)
    }

    private fun captureVideo() {
        val cameraVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (cameraVideoIntent.resolveActivity(packageManager) != null) {
            val videoFile: File? = createVideoFile()
            if (videoFile != null) {
                videoPathToFile = videoFile.absolutePath
                val videoUri = FileProvider.getUriForFile(
                    this@CameraActivity,
                    "com.example.camerawithpicture.fileprovider",
                    videoFile
                )
                cameraVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri)
                startActivityForResult(cameraVideoIntent, 3)
            }
        }
    }

    private fun chooseVideoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 4)
    }

    private fun createPhotoFile(): File? {
        /**here we provide a path for image taken from camera and time stamp*/
        val savingTime = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDirectory: File =
            getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        var imageTaken: File? = null
        try {
            imageTaken = File.createTempFile(savingTime, ".jpg", storageDirectory)
            storageDirectory.mkdir()
        } catch (ioE: IOException) {
            Log.d("myLog", "Exe: $ioE")
        }
        return imageTaken
    }

    private fun createVideoFile(): File? {
        val savingTime = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDirectory: File =
            getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        var videoTaken: File? = null
        try {
            videoTaken = File.createTempFile(savingTime, ".mp4", storageDirectory)
        } catch (ioe: IOException) {
            Log.d("myLog", "Exe: $ioe")
        }
        return videoTaken
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 ->
                    /**startActivityForResult sends photo with req code 1(taken from camera app) if equals then it updates the UI*/
                    onActivityResultForCaptureImage()
                2 ->
                    /**startActivityForResult sends photo with req code 2(selected from gallery) if equals then it updates the UI*/
                    /**here below step finds path that data come from */
                    onActivityResultForCaptureImageFromGallery(data)
                3 ->
                    /**here get data when record from camera with req code 3*/
                    onActivityResultForCaptureVideo(data)
                4 ->
                    /**here we get data from gallery*/
                    onActivityResultForCaptureVideoFromGallery(data)
            }
        }
    }

    private fun onActivityResultForCaptureImage() {
        val bitmap: Bitmap? = BitmapFactory.decodeFile(pathToFile)
//                Glide.with(MyApplication.getApplicationContext()).load(bitmap).apply(RequestOptions.circleCropTransform())
//                    .placeholder(R.drawable.plcimg).into(profileSetImage)
        profileSetImage.setImageBitmap(bitmap)
    }

    @SuppressLint("LongLogTag")
    private fun onActivityResultForCaptureImageFromGallery(data: Intent?) {

        val selectedImage = data?.data
        /**here in this step it opens the gallery toselect photos*/
        val filePath = arrayOf(MediaStore.Images.Media.DATA)
        /**here content resolver usedto select data in database or here gallery so it uses cursor to select the data*/
        val c = contentResolver.query(selectedImage!!, filePath, null, null, null)
        c!!.moveToFirst()
        /**here it moves to first position*/
        val columnIndex = c.getColumnIndex(filePath[0])
        /**here it selects the path of image that we selected*/
        val picturePath = c.getString(columnIndex)
        c.close()
        /**after selection of image it close the gallery*/
        val thumbnail = BitmapFactory.decodeFile(picturePath)
        Log.d("path of image from gallery", "picture path is $picturePath")
//        MyData.setimage("$selectedImage")
        profileSetImage.setImageBitmap(thumbnail)
        /**here selected image from gallery is updates the UI*/
    }

    private fun onActivityResultForCaptureVideo(data: Intent?) {
        val videoUri = data?.data
        /**here recorded video gets here with req code 4*/
        videoView.setVideoURI(videoUri)
        /**here video is parsed*/
        videoView.start()
        /**video starts*/
        videoView.setOnCompletionListener {
            Toast.makeText(
                MyApplication.getApplicationContext(),
                "Video Completed",
                Toast.LENGTH_LONG
            ).show()
            videoView.stopPlayback()
        }
    }

    @SuppressLint("LongLogTag")
    private fun onActivityResultForCaptureVideoFromGallery(data: Intent?) {
        val selectedVideo = data?.data
        val videoFilePath = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = contentResolver.query(selectedVideo!!, videoFilePath, null, null, null)
        cursor!!.moveToFirst()
        cursor.close()
        val videoNail = data.data
        videoView.setVideoURI(videoNail)
        videoView.start()
        videoView.setOnCompletionListener {
            Toast.makeText(
                MyApplication.getApplicationContext(),
                "Video Completed",
                Toast.LENGTH_LONG
            ).show()
            videoView.stopPlayback()
        }
    }
}