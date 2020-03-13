package com.example.camerawithpicture.recyclerViewAPiModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.camerawithpicture.R

class MyDataAdapter(private var listItem: ArrayList<ModelWithHeader> = ArrayList()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            0->{
                val v1 =
                    LayoutInflater.from(parent.context).inflate(R.layout.date_rv, parent, false)
                return DateHeaderViewHolder(v1)
            }
            1->{
                val v2 = LayoutInflater.from(parent.context).inflate(R.layout.name_status_rv, parent, false)
                return ItemViewHolder(v2)
            }
        }
        throw RuntimeException("there is no type that matches the type $viewType + make sure your using types correctly")
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            0 -> {
                val dateItem = listItem[position] as HeaderClass
                val dateHeaderViewHolder = holder as DateHeaderViewHolder
                dateHeaderViewHolder.txtDate.text = dateItem.joiningData
            }

            1 -> {
                val dataItem = listItem[position] as MyDataModel
                val itemViewHolder = holder as ItemViewHolder
                itemViewHolder.txtName.text = dataItem.name
                when {
                    dataItem.status == 0 -> itemViewHolder.statusImage.visibility =
                        View.INVISIBLE
                    dataItem.status == 1 -> itemViewHolder.statusImage.setImageResource(
                        R.drawable.active_status
                    )
                    dataItem.status == 2 -> itemViewHolder.statusImage.setImageResource(
                        R.drawable.inactive_status
                    )
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (listItem[position] is HeaderClass){
            return 0
        }else if(listItem[position] is MyDataModel){
            return 1
        }
        return -1
    }

    class DateHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtDate: TextView = itemView.findViewById(R.id.dateWise)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName: TextView = itemView.findViewById(R.id.txtName)
        var statusImage: ImageView = itemView.findViewById(R.id.nameStatus)
    }
}
