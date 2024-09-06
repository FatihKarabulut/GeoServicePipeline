package com.ahbap.geonames.GeonamesMikroService;


import com.ahbap.geonames.GeonamesMikroService.geonames.GeoFullInfo;
import com.ahbap.geonames.GeonamesMikroService.services.GeoServ;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("wiki")
@Slf4j
public class WikiControler {
    public GeoServ services;

    public WikiControler(GeoServ services) {
        this.services = services;
    }

    @GetMapping("/service")
    public GeoFullInfo wiki(@RequestParam String str, @RequestParam int maxRow)
    {
        log.info("str = {} / maxRow =  {}",str,maxRow);

        var info = services.findByWiki(str,maxRow);
        log.info("info = {}",info);
            return info;

    }
}
