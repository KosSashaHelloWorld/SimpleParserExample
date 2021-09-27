package com.example.demo.lib;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class MyConnector {
    private String link;
    private String username;
    private String password;
    private String database;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private SessionFactory factory;

    public MyConnector() {
    }

    public MyConnector(String link){
        this.link = link;
    }

    public MyConnector(String link, String username, String password) {
        this.link = link;
        this.username = username;
        this.password = password;
    }

    public MyConnector(String username, String password, String link, String database) {
        this.link = link;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void buildStandardSessionFactory(){
        assert link != null;
        assert !link.equals("");
        assert  username != null;
        assert !username.equals("");
        assert password != null;
        assert !password.equals("");
        assert database != null;
        assert !database.equals("");

        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", JDBC_DRIVER);
        settings.put("hibernate.dialect", DIALECT);
        settings.put("hibernate.connection.url", link);
        settings.put("hibernate.connection.username", username);
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.connection.datasource", database);
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");

        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(registry);

        Metadata metadata = metadataSources.buildMetadata();

        factory = metadata.buildSessionFactory();
    }

    public void buildConfigSessionFactory(){
        if (factory != null){
            System.out.println("SessionFactory is already built!");
            return;
        }
        Configuration config = new Configuration();
        config.configure();
        factory = config.buildSessionFactory();
    }

    public Session openSession(){
        assert factory != null;
        if (factory.getCurrentSession().isOpen())
        {
            return factory.getCurrentSession();
        }
        else
        {
            return factory.openSession();
        }
    }

    public void closeSessionFactory(){
        assert factory != null;
        if (factory.isOpen())
        {
            factory.close();
        }
        else
        {
            System.err.println("\nSessionFactory is already closed!\n");
        }
    }
}
