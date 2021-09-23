/**
 * Sample Skeleton for 'GUI.fxml' Controller Class
 */

package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.persistence.HibernateUtil;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class DBController {
    int ITEMS_COUNT = 200;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="ebayBut"
    private Button ebayBut; // Value injected by FXMLLoader

    @FXML // fx:id="AddBut"
    private Button AddBut; // Value injected by FXMLLoader

    @FXML // fx:id="ViewBut"
    private Button ViewBut; // Value injected by FXMLLoader

    @FXML // fx:id="DeleteBut"
    private Button DeleteBut; // Value injected by FXMLLoader

    @FXML // fx:id="progressBar"
    private ProgressBar progressBar; // Value injected by FXMLLoader

    @FXML // fx:id="searchField"
    private TextField searchField; // Value injected by FXMLLoader

    @FXML // fx:id="table"
    private TableView<Product> table; // Value injected by FXMLLoader

    @FXML // fx:id="productNameColumn"
    private TableColumn<Product, String> productNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="priceColumn"
    private TableColumn<Product, Double> priceColumn; // Value injected by FXMLLoader

    @FXML // fx:id="productPriceColumn"
    private TableColumn<Product, Double> productPriceColumn; // Value injected by FXMLLoader

    @FXML // fx:id="deliveryPriceColumn"
    private TableColumn<Product, Double> deliveryPriceColumn; // Value injected by FXMLLoader

    @FXML // fx:id="countryColumn"
    private TableColumn<Product, String> countryColumn; // Value injected by FXMLLoader

    @FXML // fx:id="seekersColumn"
    private TableColumn<Product, String> seekersColumn; // Value injected by FXMLLoader

    @FXML // fx:id="tableContextMenu"
    private ContextMenu tableContextMenu; // Value injected by FXMLLoader

    @FXML // fx:id="openLinkContextMenu"
    private MenuItem openLinkContextMenu; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert ebayBut != null : "fx:id=\"ebayBut\" was not injected: check your FXML file 'GUI.fxml'.";
        assert AddBut != null : "fx:id=\"AddBut\" was not injected: check your FXML file 'GUI.fxml'.";
        assert ViewBut != null : "fx:id=\"ViewBut\" was not injected: check your FXML file 'GUI.fxml'.";
        assert DeleteBut != null : "fx:id=\"DeleteBut\" was not injected: check your FXML file 'GUI.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'GUI.fxml'.";
        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'GUI.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'GUI.fxml'.";
        assert productNameColumn != null : "fx:id=\"productName\" was not injected: check your FXML file 'GUI.fxml'.";
        assert priceColumn != null : "fx:id=\"price\" was not injected: check your FXML file 'GUI.fxml'.";
        assert productPriceColumn != null : "fx:id=\"productPrice\" was not injected: check your FXML file 'GUI.fxml'.";
        assert deliveryPriceColumn != null : "fx:id=\"deliveryPrice\" was not injected: check your FXML file 'GUI.fxml'.";
        assert countryColumn != null : "fx:id=\"country\" was not injected: check your FXML file 'GUI.fxml'.";
        assert seekersColumn != null : "fx:id=\"seekers\" was not injected: check your FXML file 'GUI.fxml'.";
        assert tableContextMenu != null : "fx:id=\"tableContextMenu\" was not injected: check your FXML file 'GUI.fxml'.";
        assert openLinkContextMenu != null : "fx:id=\"openLinkContextMenu\" was not injected: check your FXML file 'GUI.fxml'.";

        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        deliveryPriceColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryPrice"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        seekersColumn.setCellValueFactory(new PropertyValueFactory<>("seekers"));

        ebayBut.setOnAction(ebayOnClickEvent -> {
            disableAllButtons(true);
            table.getItems().removeAll();
            progressBar.progressProperty().unbind();

            String link = "https://www.ebay.com/sch/i.html?_nkw="
                    + searchField.getText().replace(" ", "+")
                    + "&_ipg=" + ITEMS_COUNT;
            Document document = EbayHTMLParser.getDocFromLink(link);
            assert document != null;
            EbayHTMLParser task = new EbayHTMLParser(document);

            progressBar.progressProperty().bind(task.progressProperty());
            task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                    parsingEvent -> {
                        ObservableList<Product> list = task.getValue();
                        table.setItems(list);
                        disableAllButtons(false);
                    });
            Thread thread = new Thread(task);
            thread.start();
        });

        AddBut.setOnAction(event -> {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(table.getSelectionModel().getSelectedItem());
            transaction.commit();
            session.close();
        });

        openLinkContextMenu.setOnAction(openLinkEvent -> {
            String href = table.getSelectionModel().getSelectedItem().getProductHref();
            try {
                Desktop.getDesktop().browse(URI.create(href));
            } catch (java.io.IOException e) {
                System.out.println(e.getMessage());
            }
        });



    }

    private void disableAllButtons(boolean bool) {
        ebayBut.setDisable(bool);
        ViewBut.setDisable(bool);
        AddBut.setDisable(bool);
        DeleteBut.setDisable(bool);
    }
}
