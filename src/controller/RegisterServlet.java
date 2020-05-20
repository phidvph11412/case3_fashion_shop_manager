package controller;

import model.Customer;
import service.CustomerService;
import validate.ValidateCustomer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletCustomer", urlPatterns = "/Customers")

public class RegisterServlet extends HttpServlet {
    private CustomerService customerService;
    private static final String REGEX_NAME = "[A-Za-z]{5,15}$";
    private static final String REGEX_PASS = "[A-Za-z]{6,15}\\d{1,5}";
    private static final String REGEX_REPASS = "[A-Za-z]{6,15}\\d{1,5}";
    private static final String REGEX_PHONE = "^[+]\\d{2}-\\d{8,13}";
    private static final String REGEX_EMAIL = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
    private static final String REGEX_ADDRESS = "[A-Za-z]{1,15}[-|/][A-Za-z]{1,15}[-|/][A-Za-z]{1,15}";

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                try {
                    insertCustomer(request, response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "edit":
                try {
                    update(request, response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            default:
                try {
                    listCustomer(request, response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCustomer(request, response);
                break;
            case "editCustomer":
                showEditCustomer(request, response);
                break;
            default:
                try {
                    listCustomer(request, response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }
    }

    private void listCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Customer> customerList = customerService.selectAllCustomer();
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/listCustomer.jsp");
        dispatcher.forward(request, response);
    }

    private void showCustomer(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/editCustomer.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        String rePass = request.getParameter("repass");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        if (name.matches(REGEX_NAME) && pass.matches(REGEX_PASS) && rePass.matches(REGEX_REPASS) && phone.matches(REGEX_PHONE) &&
                email.matches(REGEX_EMAIL) && address.matches(REGEX_ADDRESS) && pass.equals(rePass)) {
            Customer customer = new Customer(name, pass, phone, email, address);
            customerService.insertCustomers(customer);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/register.jsp");
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/Error.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void showEditCustomer(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        Customer customer = customerService.selectCustomer(name);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/editCustomer.jsp");
        request.setAttribute("customers", customer);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String customerName = request.getParameter("name");
        customerService.deleteCustomer(customerName);
        List<Customer> listCustomer = customerService.selectAllCustomer();
        request.setAttribute("listCustomer", listCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/register.jsp");
        dispatcher.forward(request, response);
    }
    private void update(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        Customer customer = new Customer(name, pass, phone, email, address);
        customerService.updateCustomer(customer);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/editCustomer.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
