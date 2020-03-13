package com.example.camerawithpicture

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.camerawithpicture.base.BaseActivity
import com.example.camerawithpicture.cameraUi.CameraActivity
import com.example.camerawithpicture.data.MyData
import com.example.camerawithpicture.recyclerViewAPiModel.NameStatusActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        userName.setText(MyData.getUserName())
        passwordUser.setText(MyData.getPassword())
        clickListener()
    }

    private fun clickListener() {
        save.setOnClickListener {
            val name = userName.text.toString()
            val password = passwordUser.text.toString()
            MyData.setUserName(name)
            MyData.setPassword(password)
            Toast.makeText(this, "data to SP", Toast.LENGTH_LONG).show()
        }
        gotoCamera.setOnClickListener {
            val intent=Intent(this,CameraActivity::class.java)
            startActivity(intent)
        }
        gotoMultipleRv.setOnClickListener {
            val intent=Intent(this,NameStatusActivity::class.java)
            startActivity(intent)
        }
    }
}
