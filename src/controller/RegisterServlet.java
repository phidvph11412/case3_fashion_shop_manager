package controller;

import model.Customer;
import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //nhan du lieu luu vao database
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("rePassword");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        CustomerService customerService = new CustomerService();
        Customer customer = new Customer(userName, password, phoneNumber, email, address);

        boolean isOk = customerService.saveDataCustomer(customer);
        if (isOk) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
