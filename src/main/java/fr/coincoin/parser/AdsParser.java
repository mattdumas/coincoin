package fr.coincoin.parser;

import fr.coincoin.domain.Ad;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AdsParser {

    public List<Ad> parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements rawAds = doc.select(".list-lbc a");
        return rawAds.stream()
                .map(rawAd ->
                        new Ad.Builder()
                                .withName(extractTitle(rawAd))
                                .withUrl(extractUrl(rawAd))
                                .withImageUrl(extractImage(rawAd))
                                .withPrice(extractPrice(rawAd))
                                .build())
                .collect(Collectors.toList());
    }

    private String extractTitle(Element x) {
        Element rawTitle = x.select(".title").first();
        return rawTitle != null ? rawTitle.text() : "";
    }

    private String extractUrl(Element x) {
        return x.attr("href");
    }

    private String extractImage(Element x) {
        Element rawImage = x.select(".image img").first();
        return rawImage != null ? rawImage.attr("src") : "";
    }

    private String extractPrice(Element x) {
        Element rawPrice = x.select(".price").first();
        return rawPrice != null ? rawPrice.text() : "";
    }

}
