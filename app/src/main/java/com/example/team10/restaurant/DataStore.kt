package com.example.team10.restaurant

import com.example.team10.R

class DataStore {
    fun getDataSet(): List<Restaurant> {
        return listOf(
            Restaurant( "Nguyen Quang Hai", "Footballer", R.drawable.quanghai),
            Restaurant("Bui Tien Dung", "Footballer", R.drawable.buitiendung),
            Restaurant( "Duy Manh", "Footballer", R.drawable.duymanh),
            Restaurant( "Cong Phuong", "Footballer", R.drawable.congphuong),
            Restaurant( "Van Toan", "Footballer", R.drawable.vantoan),
            Restaurant( "Nguyen Quang Hai 6", "Footballer", R.drawable.quanghai),
            Restaurant("Bui Tien Dung 7", "Footballer", R.drawable.buitiendung),
            Restaurant( "Duy Manh 8", "Footballer", R.drawable.duymanh),
            Restaurant( "Cong Phuong 11", "Footballer", R.drawable.congphuong),
            Restaurant( "Van Toan 10", "Footballer", R.drawable.vantoan),
            Restaurant( "Nguyen Quang Hai 12", "Footballer", R.drawable.quanghai),
            Restaurant("Bui Tien Dung 13", "Footballer", R.drawable.buitiendung),
            Restaurant( "Duy Manh 13", "Footballer", R.drawable.duymanh),
            Restaurant( "Cong Phuong 16", "Footballer", R.drawable.congphuong),
            Restaurant( "Van Toan 15", "Footballer", R.drawable.vantoan),
        )
    }
}