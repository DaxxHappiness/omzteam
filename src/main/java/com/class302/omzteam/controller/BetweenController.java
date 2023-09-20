package com.class302.omzteam.controller;

import com.class302.omzteam.between.service.WebScraperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/between")
@Log4j2
public class BetweenController {

    @Autowired
    WebScraperService webScraperService;


    @GetMapping("/ex31")
    public String ex3(Model model) {
        webScraperService.scrapeWebsite(model);
        return "/between/ex31";


    }

    @PostMapping("/updateScrapeData")
    @ResponseBody
    public String updateScrapeData(@RequestParam String model, Model model2) {
        String updatedData = "";

        // 선택한 모델에 따라 다른 데이터 생성
        if ("인텔 코어i5-13세대 13400F (랩터레이크)".equals(model)) {
            updatedData = (String) model2.getAttribute("scrapeData");
        } else if ("모델 2".equals(model)) {
            updatedData = "가격: 200,000원, 성능: 보통";
        } else if ("모델 3".equals(model)) {
            updatedData = "가격: 150,000원, 성능: 저하";
        }

        return updatedData;
    }














}


