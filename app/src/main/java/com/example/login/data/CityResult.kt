package com.example.login.data

data class CityResult (

    val data : List<Data>,
    val metadata : Metadata
)

data class Data (

    val id : Int,
    val wikiDataId : String,
    val type : String,
    val city : String,
    val name : String,
    val country : String,
    val countryCode : String,
    val region : String,
    val regionCode : String,
    val latitude : Double,
    val longitude : Double,
    val population : Int
)

data class Metadata (

    val currentOffset : Int,
    val totalCount : Int
)