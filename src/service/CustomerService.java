package service;

import model.Customer;

import java.sql.ResultSet;

public class CustomerService implements ICustomerService {
    DAL dal;

    public CustomerService() {
        dal = new DAL();
    }

    public ResultSet getListCustomer() {
        return dal.getData("select * from customer");
    }

    public ResultSet getNameAndPasswordListCustomer() {
        return dal.getData("select customerName, customerPass from customer;");
    }

    public boolean saveDataCustomer(Customer customer) {
        return dal.updateData("insert into customer() " +
                "values('" + customer.getCustomerName() + "', '" + customer.getCustomerPassword() + "', '" + customer.getCustomerPhoneNumber() + "',' " + customer.getCustomerEmail() + "' , '" + customer.getCustomerAddress() + "');");
    }
}
