package com.example.camerawithpicture.recyclerViewAPiModel

class NameStatusModel(
    var name: String,
    var date: String,
    var status: Int,
    var type: Int
)
{
    companion object {
        const val DATE_HEADER = 0
        const val DATA_BODY = 1
    }
}