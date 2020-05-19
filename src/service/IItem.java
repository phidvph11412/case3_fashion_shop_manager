package service;

import model.Item;

import java.sql.ResultSet;

public interface IItem {
    public ResultSet getListItem();
    public ResultSet getListItemID();
    public ResultSet getItemByID(String id);
    public boolean saveDataItem(Item item);
    public boolean deleteItemByID(String id);
    public boolean updateItemByID(String id , Item item);
}
