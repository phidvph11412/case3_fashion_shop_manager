package controller;

import model.Customer;
import service.CustomerService;
import service.ItemService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerService customerService = new CustomerService();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        List<Customer> listNameAndPassCustomer = customerService.getListUserAndPass();
       for (Customer customer : listNameAndPassCustomer) {
           if (userName.equals(customer.getCustomerName()) && password.equals(customer.getCustomerPassword())){
               RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
               HttpSession session = request.getSession();
               session.setAttribute("userName", userName);
               requestDispatcher.forward(request, response);
           }
       }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
        request.setAttribute("message", "user name and password is invalid!!");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
