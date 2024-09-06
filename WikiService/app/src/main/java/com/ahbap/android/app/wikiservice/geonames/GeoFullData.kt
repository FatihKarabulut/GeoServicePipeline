package com.ahbap.android.app.wikiservice.geonames

import kotlinx.serialization.Serializable

@Serializable
class GeoFullData {
    var summary: String? = null
    var elevation: Int = 0
    var feature: String? = null
    var lng: Double = 0.0
    var countryCode: String? = null
    var rank: Int = 0
    var thumbnailImg: String? = null
    var lang: String? = null
    var title: String? = null
    var lat: Double = 0.0
    var wikipediaUrl: String? = null
    var geoNameId: Int = 0
    var continent: String? = null
    var capital: String? = null
    var languages: String? = null
    var geonameId: Int = 0
    var south: Double = 0.0
    var isoAlpha3: String? = null
    var north: Double = 0.0
    var fipsCode: String? = null
    var population: String? = null
    var east: Double = 0.0
    var isoNumeric: String? = null
    var areaInSqKm: String? = null
    var west: Double = 0.0
    var countryName: String? = null
    var postalCodeFormat: String? = null
    var continentName: String? = null
    var currencyCode: String? = null

    override fun toString(): String {
        return "$summary $title $lng $lang $east $north $countryName $population"
    }
}

