package service;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService implements ICustomerService {
    private String url = "jdbc:mysql://localhost:3306/lucy_shop";
    private String user = "root";
    private String pass = "";
    private static final String SELECT_ALL_CUSTOMER = "select * from customer";
    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customer (customerName,customerPass,Phone,Email,Address) VALUES (?, ?, ?,?,?);";
    private static final String DELETE_Customer_SQL = "delete from customer where customerName = ?;";
    private static final String SELECT_CUSTOMER_NAME = "select customerName,customerPass,Phone,Email,Address from customer where customerName = ? ;";
    private static final String UPDATE_CUSTOMER_NAME = "update customer set  customerPass = ?,Phone = ?,Email = ? ,Address = ? where customerName = ?";

    public CustomerService() {

    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            printSQLException(throwables);
        }
        return connection;
    }

    @Override
    public List<Customer> selectAllCustomer() {
        List<Customer> customer = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMER);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("customerName");
                String pass = rs.getString("customerPass");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                customer.add(new Customer(name, pass, phone, email, address));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return customer;
    }

    @Override
    public void insertCustomers(Customer customer) throws SQLException {
        System.out.println(INSERT_CUSTOMER_SQL);
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_SQL);
        preparedStatement.setString(1, customer.getCustomerName());
        preparedStatement.setString(2, customer.getCustomerPassword());
        preparedStatement.setString(3, customer.getCustomerPhoneNumber());
        preparedStatement.setString(4, customer.getCustomerEmail());
        preparedStatement.setString(5, customer.getCustomerAddress());
        preparedStatement.executeUpdate();
    }

    @Override
    public Customer selectCustomer(String customerName) {
        Customer customer = null;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_NAME);
            preparedStatement.setString(1, customerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("customerName");
                String pass = resultSet.getString("customerPass");
                String phone = resultSet.getString("Phone");
                String email = resultSet.getString("Email");
                String address = resultSet.getNString("Address");
                customer = new Customer(name, pass, phone, email, address);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        boolean rowUpdate = false;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER_NAME);
            preparedStatement.setString(5, customer.getCustomerName());
            preparedStatement.setString(1, customer.getCustomerPassword());
            preparedStatement.setString(2, customer.getCustomerPhoneNumber());
            preparedStatement.setString(3, customer.getCustomerEmail());
            preparedStatement.setString(4, customer.getCustomerAddress());
            rowUpdate = preparedStatement.executeUpdate() > 0;
            System.out.println(preparedStatement);
        } catch (SQLException ex) {
            printSQLException(ex);
        }
        return rowUpdate;
    }

    @Override
    public boolean deleteCustomer(String customerName) throws SQLException {
        return false;
    }

    @Override
    public List<Customer> searchCustomer(String customerName) throws SQLException {
        return null;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = e.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
