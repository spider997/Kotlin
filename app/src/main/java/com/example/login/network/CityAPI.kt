package com.example.login.network

import com.example.login.data.CityResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
//interfész nyitása a lekérdezésekhez
// URL :  https://wft-geo-db.p.rapidapi.com/v1/geo/cities?limit=5&offset=0&namePrefix=debrecen&rapidapi-key=b2e9f8d5f8msh59247e52dcadc38p1b4415jsnee1acdfe662c
// HOST : https://wft-geo-db.p.rapidapi.com
// PATH : /v1/geo/cities
// QUERY :?limit=5&offset=0&namePrefix=debrecen&rapidapi-key=...
interface CityAPI{

    @GET("v1/geo/cities")
    fun getCityDetails(

        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("namePrefix") city: String,
        @Query("rapidapi-key") appid: String

    ): Call<CityResult>

}