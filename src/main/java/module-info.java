module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.sql;
    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires org.jsoup;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires java.persistence;
    requires java.desktop;
    requires java.naming;

    opens com.example.demo to javafx.fxml, javafx.base;
    opens com.example.demo.entity to javafx.base, org.hibernate.orm.core;

    exports com.example.demo to javafx.fxml, javafx.graphics;
}