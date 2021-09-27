package com.example.demo.persistence;


import com.example.demo.lib.MyConnector;
import org.hibernate.Session;

public class HibernateUtil {
    private static final MyConnector connector = new MyConnector();

    public static void buildSessionFactory(){
        connector.buildConfigSessionFactory();
    }

    public static Session openSession(){
        return connector.openSession();
    }

    public static void closeSession(){
        connector.openSession().close();
    }

    public static void closeSessionFactory(){
        connector.closeSessionFactory();
    }
}