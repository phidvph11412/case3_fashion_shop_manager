package controller;

import model.Item;
import service.ItemService;
import validate.ValidateItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ItemAdminServlet", urlPatterns = "/item-update")
public class ItemAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println(action);
        switch (action) {
            case "delete":
                deleteItem(request, response);
                break;
            case "edit":
                editItem(request, response);
                break;
            case "add":
                addItem(request, response);
                break;
        }
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ItemService itemService = new ItemService();
        ValidateItem validateItem = new ValidateItem();
        String itemID = request.getParameter("itemID");
        String itemName = request.getParameter("itemName");
        String itemImage = request.getParameter("itemImage");
        String itemPrice = (request.getParameter("itemPrice"));
        String itemAmount = (request.getParameter("itemAmount"));
        String itemCategory = request.getParameter("itemCategory");
        String itemDescribe = request.getParameter("itemDescribe");
        boolean isItem = validateItem.isItem(itemID, itemName, itemImage, itemPrice, itemAmount, itemCategory, itemDescribe);
        if (isItem) {
            float price = Float.valueOf(itemPrice);
            int amount = Integer.valueOf(itemAmount);
            Item item = new Item(itemID, itemName, itemImage, price, amount, itemCategory, itemDescribe);
            boolean isSaved = itemService.saveDataItem(item);
            if (isSaved) {
                request.setAttribute("message", "save successfully");
            } else {
                request.setAttribute("message", "save not successfully . Item id  already exists !");
            }
            request.getRequestDispatcher("jsp/admin/item.jsp").forward(request, response);
        }else {
            request.setAttribute("message", "input invalid");
            request.getRequestDispatcher("jsp/admin/item.jsp").forward(request, response);
        }



    }

    private void editItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ItemService itemService = new ItemService();
        ValidateItem validateItem = new ValidateItem();
        String itemID = request.getParameter("itemID");
        String itemName = request.getParameter("itemName");
        String itemImage = request.getParameter("itemImage");
        String itemPrice = request.getParameter("itemPrice");
        String itemAmount = request.getParameter("itemAmount");
        String itemCategory = request.getParameter("itemCategory");
        String itemDescribe = request.getParameter("itemDescribe");
        boolean isItem = validateItem.isItem(itemID, itemName, itemImage, itemPrice, itemAmount, itemCategory, itemDescribe);
        if (isItem){
            float price = Float.valueOf(itemPrice);
            int amount = Integer.valueOf(itemAmount);
            Item item = new Item(itemID, itemName, itemImage, price, amount, itemCategory, itemDescribe);
            boolean isEdited = itemService.editItemByID(itemID, item);
            if (isEdited) {
                request.setAttribute("message", "edit successfully");
            } else {
                request.setAttribute("message", "edit not successfully , id not found!!");
            }
            request.getRequestDispatcher("jsp/admin/item.jsp").forward(request, response);
        }else {
            request.setAttribute("message", "input  invalid");
            request.getRequestDispatcher("jsp/admin/item.jsp").forward(request, response);
        }



    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ItemService itemService = new ItemService();
        String itemID = request.getParameter("itemID");
        boolean isDeleted = itemService.deleteItemByID(itemID);
        if (isDeleted) {
            request.setAttribute("message", "delete successfully");
        } else {
            request.setAttribute("message", "delete not successfully . item already exists in shopping cart or id not found!!");
        }
        request.getRequestDispatcher("jsp/admin/item.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
