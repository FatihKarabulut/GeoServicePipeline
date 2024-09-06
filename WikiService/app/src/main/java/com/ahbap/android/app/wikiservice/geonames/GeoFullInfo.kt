package com.ahbap.android.app.wikiservice.geonames

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class GeoFullInfo ( @SerializedName("geoFullInfos") var geonames : List<GeoFullData> = emptyList())
