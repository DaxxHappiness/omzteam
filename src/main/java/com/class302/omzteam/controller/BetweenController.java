package com.class302.omzteam.controller;

import com.class302.omzteam.between.model.ScraperData;
import com.class302.omzteam.between.service.WebScraperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/between")
@Log4j2
public class BetweenController {

    @Autowired
    WebScraperService webScraperService;


    @GetMapping("/ex31")
    public String ex3(Model model) {
        // 스크래핑한 데이터를 ScraperData 객체로 받아옴
        ScraperData scraperData = webScraperService.scrapeWebsite();
        model.addAttribute("scraperData", scraperData);

        return "/between/ex31";
    }

    @PostMapping("/updateScrapeData")
    @ResponseBody
    public String updateScrapeData(@RequestParam("model2") String model2) {
        String updatedData = "";

        ScraperData scraperData = webScraperService.scrapeWebsite();

        // 선택한 모델에 따라 다른 데이터 생성
        if ("Core i9-11900K".equals(model2)) {
            // 원하는 데이터를 ScraperData 객체에 설정
            updatedData = scraperData.getScrapData1();
        } else if ("Ryzen 9 5900X".equals(model2)) {
            updatedData = scraperData.getScrapData2();
        } else if ("모델 3".equals(model2)) {
            // 원하는 데이터를 ScraperData 객체에 설정
            // scraperData.setScrapeData2("가격: 150,000원, 성능: 저하");
        }

        System.out.println("Received model: " + scraperData);
        return updatedData;
    }

















}


