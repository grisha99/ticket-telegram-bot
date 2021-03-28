package ru.grishenko.ticketing.telegram.sevices;

import ru.grishenko.ticketing.telegram.dao.CustomerDAO;
import ru.grishenko.ticketing.telegram.entities.Customer;

import java.util.List;

public class CustomerService {

    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public List<Customer> getCustomerStopList() {
        return customerDAO.getStopListCustomers();
    }

    public List<Customer> getCustomerNoOne() {
        return customerDAO.getNoOneCustomers();
    }
}
