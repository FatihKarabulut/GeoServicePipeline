package com.ahbap.android.app.wikiservice.geonames

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ServiceComponent::class)
object RetrofitModule {
    val BASE_URL = "http://10.0.2.2:6767"
    @Provides
    fun createRetrofit() : Retrofit
    {
        val gson = GsonConverterFactory.create(GsonBuilder().setLenient().create())
        val build = OkHttpClient.Builder().build()
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(gson)
            .client(build).build()
    }
}