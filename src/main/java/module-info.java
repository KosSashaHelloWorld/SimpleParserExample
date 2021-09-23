module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires org.jsoup;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.desktop;
    requires java.naming;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.entity;
    opens com.example.demo.entity to javafx.fxml;
    exports com.example.demo.persistence;
    opens com.example.demo.persistence to javafx.fxml;
}