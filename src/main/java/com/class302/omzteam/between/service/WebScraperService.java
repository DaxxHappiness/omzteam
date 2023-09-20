package com.class302.omzteam.between.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class WebScraperService {
    public void scrapeWebsite(Model model) {
        try {
            // 웹 페이지 URL 지정
            String url1 = "https://prod.danawa.com/info/?pcode=18640280&cate=11345419&adinflow=Y";
            String url2 = "https://finance.naver.com/marketindex/";

            // 웹 페이지 가져오기
            Document doc1 = Jsoup.connect(url1).get();
            Document doc2 = Jsoup.connect(url2).get();

            // 첫 번째 웹 사이트에서 가격 정보 가져오기
            Element priceElement = doc1.selectFirst("em.prc_c");
            assert priceElement != null;
            String priceValue = priceElement.ownText();

            // 두 번째 웹 사이트에서 환율 정보 가져오기
            Element exchangeRateElement = doc2.selectFirst("span.value");
            assert exchangeRateElement != null;
            String exchangeRateValue = exchangeRateElement.ownText();

            System.out.println("가격: " + priceValue);
            System.out.println("환율: " + exchangeRateValue);

            // 가격에서 "원" 문자열 제거하고 쉼표(,) 제거 후 파싱
            priceValue = priceValue.replace("원", "").replace(",", "");
            double price = Double.parseDouble(priceValue);

            // 환율에서 쉼표(,) 제거 후 파싱
            exchangeRateValue = exchangeRateValue.replace(",", "");
            double exchangeRate = Double.parseDouble(exchangeRateValue);

            // 가격에 환율을 적용하여 계산
            double priceInDollars = price / exchangeRate;

            model.addAttribute("scrapeData", priceValue + "원"); // "원" 추가
            model.addAttribute("scrapeData2", exchangeRateValue);
            model.addAttribute("priceInDollars", priceInDollars);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
