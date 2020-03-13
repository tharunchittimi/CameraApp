package com.example.camerawithpicture.recyclerViewAPiModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.camerawithpicture.R

class NameStatusAdapter(private val nameStatusModel: ArrayList<NameStatusModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        when (viewType) {
            NameStatusModel.DATE_HEADER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.date_rv, parent, false)
                return DateTypeViewHolder(view)
            }
            NameStatusModel.DATA_BODY -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.name_status_rv, parent, false)
                return DataTypeViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.no_data_found, parent, false)
                return DateTypeViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return nameStatusModel.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val objNameStatusModel: NameStatusModel = nameStatusModel[position]
        when (objNameStatusModel.type) {
            NameStatusModel.DATE_HEADER -> {
                (holder as DateTypeViewHolder).bind(nameStatusModel[position])
            }
            NameStatusModel.DATA_BODY -> {
                (holder as DataTypeViewHolder).bind(nameStatusModel[position])
            }
        }
    }

    class DateTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(nameStatusModel: NameStatusModel) {
            dateWise.text = nameStatusModel.date
        }

        private val dateWise: TextView = itemView.findViewById(R.id.dateWise)
    }

    class DataTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(nameStatusModel: NameStatusModel) {
            txtName.text = nameStatusModel.name
            when {
                nameStatusModel.status == 0 -> nameStatus.visibility = View.INVISIBLE
                nameStatusModel.status == 1 -> nameStatus.setImageResource(R.drawable.active_status)
                nameStatusModel.status == 2 -> nameStatus.setImageResource(R.drawable.inactive_status)
            }
        }

        private val txtName: TextView = itemView.findViewById(R.id.txtName)
        private val nameStatus: ImageView = itemView.findViewById(R.id.nameStatus)
    }

    override fun getItemViewType(position: Int): Int {
        when (nameStatusModel[position].type) {
            0 -> return NameStatusModel.DATE_HEADER
            1 -> return NameStatusModel.DATA_BODY
        }
        return -1
    }
}