package com.ahbap.android.app.wikiservice.geonames

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import retrofit2.Retrofit


@Module
@InstallIn(ServiceComponent::class)
object GeonamesModule {

    @Provides
    fun createGeonames(retrofit: Retrofit) : IGeonames = retrofit.create(IGeonames::class.java)

}