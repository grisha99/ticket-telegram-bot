package ru.grishenko.ticketing.telegram;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBFactory {

    private SessionFactory factory;

    private static DBFactory dbFactory;

    private DBFactory() {
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public static DBFactory getInstance() {
        if (dbFactory == null) {
            dbFactory = new DBFactory();
        }
        return dbFactory;
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public Session getSession() {
        return getFactory().getCurrentSession();
    }
}
