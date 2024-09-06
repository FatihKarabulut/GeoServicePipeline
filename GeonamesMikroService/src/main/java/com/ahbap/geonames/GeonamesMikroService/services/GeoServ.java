package com.ahbap.geonames.GeonamesMikroService.services;

import com.ahbap.geonames.GeonamesMikroService.geonames.*;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@Slf4j
public class GeoServ {
    public RestTemplate rest;
    private static final String m_Wiki_URL = "http://api.geonames.org/wikipediaSearchJSON?q=%s&maxRows=%d&username=fatihkarabulut";
    private static final String m_COUNTRY_INFO = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=it&country=tr&username=fatihkarabulut&style=full";
    public GeoServ(RestTemplate rest) {
        this.rest = rest;
    }
    private void country(Country country,Geo geo, GeoFullInfo list )
    {
        list.geoFullInfos.add(new GeoFulData(geo.summary,geo.elevation,geo.feature,
                geo.lng,geo.countryCode,geo.rank,geo.thumbnailImg,
                geo.lang,geo.title, geo.lat,geo.wikipediaUrl,
                geo.geoNameId,country.continent,country.capital,country.languages,
                country.geonameId,country.south,country.isoAlpha3,country.north,
                country.fipsCode,country.population,country.east,country.isoNumeric,country.areaInSqKm,
                country.west,country.countryName,country.postalCodeFormat,country.continentName,country.currencyCode));
    }
    private void country(Geo geo, GeoFullInfo list)
    {

        var result = rest.getForObject(m_COUNTRY_INFO,CountryInfo.class);
        result.geonames.forEach(e -> country(e,geo,list));

    }
    private GeoFullInfo geoFul(GeonamesInfo wiki)
    {
        var info = new GeoFullInfo();
        info.geoFullInfos = new ArrayList<>();

        wiki.geonames.forEach(e -> country(e,info));

        return info;
    }
    public GeoFullInfo findByWiki(String str, int maxRow)
    {
        var wiki_url = m_Wiki_URL.formatted(str,maxRow);
        log.info("Wiki_Url {}",wiki_url);

        log.info("url {}",wiki_url);
        var wiki = rest.getForObject(wiki_url,GeonamesInfo.class);
        var info = geoFul(wiki);


        return info;
    }


}
