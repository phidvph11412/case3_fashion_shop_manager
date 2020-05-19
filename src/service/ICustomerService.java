package service;

import model.Customer;

import java.sql.ResultSet;

public interface ICustomerService {
    public ResultSet getListCustomer();
    public ResultSet getNameAndPasswordListCustomer();
    public boolean saveDataCustomer(Customer customer);
}
