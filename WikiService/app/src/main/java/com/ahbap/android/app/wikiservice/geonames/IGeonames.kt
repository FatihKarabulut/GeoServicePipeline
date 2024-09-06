package com.ahbap.android.app.wikiservice.geonames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IGeonames {

    @GET("/wiki/service")
    fun findByGeonames(@Query("str")str : String,@Query("maxRow")maxRow:Int) : Call<GeoFullInfo>
}