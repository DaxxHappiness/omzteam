package com.class302.omzteam.between.service;

import com.class302.omzteam.between.model.ScraperData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class WebScraperService {

    public ScraperData scrapeWebsite() {
        ScraperData scraperData = new ScraperData();

        try {
            // 웹 페이지 URL 지정

            // 인텔 코어i9-11세대 11900K
            String url1 = "https://prod.danawa.com/info/?pcode=13620026&keyword=Core+i9-11900K&cate=113973";

            // 라이젠9-4세대 5900X
            String url2 = "https://prod.danawa.com/info/?pcode=12622091&keyword=Ryzen+9+5900X&cate=113990";

            // 네이버 환율 정보 URL
            String urlRate = "https://finance.naver.com/marketindex/";

            // 웹 페이지 가져오기
            Document doc1 = Jsoup.connect(url1).get();
            Document doc2 = Jsoup.connect(url2).get();
            Document doc3 = Jsoup.connect(urlRate).get();

            // 인텔 코어i9-11세대 11900K 가격
            Element priceElement = doc1.selectFirst("em.prc_c");
            assert priceElement != null;
            String priceValue = priceElement.ownText();

            // 라이젠9-4세대 5900X 가격
            Element priceElement2 = doc2.selectFirst("em.prc_c");
            assert priceElement2 != null;
            String priceValue2 = priceElement2.ownText();

            // 환율 정보 가져오기
            Element exchangeRateElement = doc3.selectFirst("span.value");
            assert exchangeRateElement != null;
            String exchangeRateValue = exchangeRateElement.ownText();

            System.out.println("가격: " + priceValue);
            System.out.println("가격: " + priceValue2);
            System.out.println("환율: " + exchangeRateValue);

            // 가격 문자열에서 "원" 및 쉼표(,) 제거 후 파싱
            priceValue = priceValue.replace("원", "").replace(",", "");
            double price = Double.parseDouble(priceValue);

            priceValue2 = priceValue2.replace("원", "").replace(",", "");
            double price2 = Double.parseDouble(priceValue2);

            // 환율에서 쉼표(,) 제거 후 파싱
            exchangeRateValue = exchangeRateValue.replace(",", "");
            double exchangeRate = Double.parseDouble(exchangeRateValue);

            // 가격에 환율을 적용하여 계산
            double priceInDollars = price / exchangeRate;
            double priceInDollars2 = price2 / exchangeRate;

            // ScraperData 객체에 데이터 설정
            scraperData.setRate(exchangeRate); // 환율
            scraperData.setScrapData1(priceValue); // 인텔 코어i9-11세대 11900K 가격
            scraperData.setScrapData2(priceValue2); // 라이젠9-4세대 5900X 가격
            scraperData.setRateScrap(priceInDollars); // 인텔 코어i9-11세대 11900K 환율 적용
            scraperData.setRateScrap2(priceInDollars2); // 라이젠9-4세대 5900X 환율 적용

        } catch (Exception e) {
            e.printStackTrace();
        }
        return scraperData;
    }

}
