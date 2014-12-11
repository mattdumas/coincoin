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

        return rawAds.stream().map(x -> {
            Ad.Builder builder = new Ad.Builder();
            Element rawTitle = x.select(".title").first();
            String adUrl = x.attr("href");
            String imageUrl = x.select(".image img").first().attr("src");

            builder.withName(rawTitle.text())
                    .withUrl(adUrl)
                    .withImageUrl(imageUrl)
                    .withPrice(x.select(".price").first().text());

            return builder.build();

        }).collect(Collectors.toList());

    }


}
