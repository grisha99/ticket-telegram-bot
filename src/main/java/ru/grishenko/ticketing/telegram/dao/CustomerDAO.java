package ru.grishenko.ticketing.telegram.dao;

import org.hibernate.Session;
import ru.grishenko.ticketing.telegram.DBFactory;
import ru.grishenko.ticketing.telegram.entities.Customer;

import java.util.List;

public class CustomerDAO {

    private Session session;

    public List<Customer> getStopListCustomers() {
        session = DBFactory.getInstance().getSession();
        session.getTransaction().begin();
        List<Customer> customerList = session.createQuery("from Customer c where c.stopList>0").getResultList();
//        List<Customer> customerList = session.createQuery("select from Customer as c where c.fStopList>0", Customer.class).getResultList();
//        List<Product> result = new ArrayList<>(buyer.getProducts());
//        List<Product> result = buyer.getProducts();                       // такой запрос вызывает org.hibernate.LazyInitializationException:
        //        failed to lazily initialize a collection of role: ru.grishchenko.models.Buyer.products,
        //        could not initialize proxy - no Session
        // Видимо что-то связано с контекстом постоянства, в чем может быть пролема?
        session.getTransaction().commit();

        return customerList;
    }

    public List<Customer> getNoOneCustomers() {
        session = DBFactory.getInstance().getSession();
        session.getTransaction().begin();

        List<Customer> customerList = session.createQuery("from Customer c where c.type=0").getResultList();

        session.getTransaction().commit();

        return customerList;
    }
}
