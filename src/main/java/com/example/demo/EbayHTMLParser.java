package com.example.demo;

import com.example.demo.entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class EbayHTMLParser extends Task<ObservableList<Product>> {
    private final Elements elements;

    public static Document getDocFromLink(String link){
        Connection connection = Jsoup.connect(link);
        try {
            return connection.post();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public EbayHTMLParser(Document document) {
        elements = document.getElementsByClass("s-item__info clearfix");
    }

    @Override
    protected ObservableList<Product> call() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        for (Element element : elements) {
            if (getProductName(element).equals("")) continue;
            list.add(getProduct(element));
            this.updateProgress(list.size(), elements.size());
        }
        this.updateProgress(1, 1);
        return list;
    }

    private Product getProduct(Element e){
        String productName = getProductName(e);
        double productPrice = getProductPrice(e);
        double deliveryPrice = getDeliveryPrice(e);
        String country = getCountry(e);
        String seekers = getSeekers(e);
        String productHref = getProductHref(e);

        return new Product(productName, productPrice, deliveryPrice, country, seekers, productHref);
    }

    private String getProductName(Element e){
        return e.getElementsByClass("s-item__title").text();
    }

    private double getProductPrice(Element e){
        String priceStr = e.getElementsByClass("s-item__price").text();
        return PriceParser.parsePrice(priceStr);
    }

    private double getDeliveryPrice(Element e){
        String deliveryStr = e.getElementsByClass("s-item__shipping s-item__logisticsCost").text();
        return PriceParser.parseDelivery(deliveryStr);
    }

    private String getCountry(Element e){
        String country = e.getElementsByClass("s-item__location s-item__itemLocation").text();
        String[] aCountry = country.split("от ");
        country = aCountry[1];
        return country;
    }

    private String getSeekers(Element e){
        String seekers = e.getElementsByClass("s-item__hotness s-item__itemHotness").text();
        if (seekers.equals("")) {
            seekers = "0 отслеживающих";
        }
        if (seekers.equals("Последний")) {
            seekers = seekers + " "
                    + e.getElementsByClass("s-item__dynamic s-item__additionalItemHotness").text();
        }
        return seekers;
    }

    private String getProductHref(Element e){
        return e.getElementsByClass("s-item__link").attr("href");
    }
}