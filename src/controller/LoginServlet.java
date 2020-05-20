package controller;

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

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        ResultSet listNameAndPassCustomer =
        while (true){
            try {
                if (!listNameAndPassCustomer.next()) break;
                if (userName.equals(listNameAndPassCustomer.getString(1)) && password.equals(listNameAndPassCustomer.getString(2))){
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                    HttpSession session = request.getSession();
                    session.setAttribute("userName", userName);
                    requestDispatcher.forward(request, response);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
        request.setAttribute("message", "user name and password is invalid!!");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}