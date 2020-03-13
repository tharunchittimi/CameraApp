package com.example.camerawithpicture.recyclerViewAPiModel

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.camerawithpicture.R
import com.example.camerawithpicture.base.BaseActivity
import kotlinx.android.synthetic.main.activity_name_status.*

class NameStatusActivity : BaseActivity() {
    lateinit var myDataModel: ArrayList<MyDataModel>
    private var outputList: ArrayList<ModelWithHeader>? = null
    private var previousDate = ""
    override fun setLayout(): Int {
        return R.layout.activity_name_status
    }

    override fun initView(savedInstanceState: Bundle?) {
        myDataModel = ArrayList()
        outputList = ArrayList()
        myDataModel.add(MyDataModel("Tharun", "11 Jan 1997", 0))
        myDataModel.add(MyDataModel("Ramya", "13 Jan 1997", 1))
        myDataModel.add(MyDataModel("Tharun Reddy", "19 Mar 1997", 2))
        myDataModel.add(MyDataModel("Vignesh", "30 Apr 1997", 2))
        myDataModel.add(MyDataModel("Gouse", "31 May 1997", 1))
        myDataModel.add(MyDataModel("Gouse Moula", "31 May 1997", 1))
        myDataModel.add(MyDataModel("Kishore", "30 Jun 1997", 2))
        myDataModel.add(MyDataModel("Tharun", "09 Jul 1997", 0))
        myDataModel.add(MyDataModel("Lahari", "19 Aug 1997", 2))
        myDataModel.add(MyDataModel("Lahari Lahari", "19 Aug 1997", 2))
        myDataModel.add(MyDataModel("Dheeraj", "09 Sep 1997", 2))
        myDataModel.add(MyDataModel("Kanya Kumari ", "09 Sep 1997", 1))
        myDataModel.add(MyDataModel("Kanya ", "09 Sep 1997", 1))
        myDataModel.add(MyDataModel("Kanya kumari ", "10 Sep 1997", 1))

        for ((position, item) in myDataModel.withIndex()) {
            if (position == 0) {
                previousDate = item.joiningData
                outputList?.add(HeaderClass(item.joiningData))
                outputList?.add(item)
            } else if (previousDate != item.joiningData) {
                previousDate = item.joiningData
                outputList?.add(HeaderClass(item.joiningData))
                outputList?.add(item)
            } else {
                outputList?.add(item)
            }
        }

        recyclerViewData.layoutManager = LinearLayoutManager(this)
        val myDataAdapter = MyDataAdapter(outputList!!)
        recyclerViewData.adapter = myDataAdapter
        myDataAdapter.notifyDataSetChanged()

    }
}

